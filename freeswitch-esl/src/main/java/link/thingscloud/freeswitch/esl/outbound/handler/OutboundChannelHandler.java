/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.outbound.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.DefaultThreadFactory;
import link.thingscloud.freeswitch.esl.helper.EslHelper;
import link.thingscloud.freeswitch.esl.outbound.listener.ChannelEventListener;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.event.EslEventHeaderNames;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import link.thingscloud.freeswitch.esl.util.RemotingUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>OutboundChannelHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Slf4j
public class OutboundChannelHandler extends SimpleChannelInboundHandler<EslMessage> {

    private static final String MESSAGE_TERMINATOR = "\n\n";
    private static final String LINE_TERMINATOR = "\n";

    private final Lock syncLock = new ReentrantLock();
    private final Queue<SyncCallback> syncCallbacks = new ConcurrentLinkedQueue<>();
    private final ChannelEventListener listener;
    private final ExecutorService publicExecutor;
    private final ConcurrentHashMap<String, CompletableFuture<EslEvent>> backgroundJobs =
            new ConcurrentHashMap<>();

    private final ExecutorService backgroundJobExecutor = new ScheduledThreadPoolExecutor(8,
            new DefaultThreadFactory("Outbound-BackgroundJob-Executor", true));

    private final boolean isTraceEnabled = log.isTraceEnabled();
    private final ConcurrentLinkedQueue<CompletableFuture<EslMessage>> apiCalls =
            new ConcurrentLinkedQueue<>();
    private Channel channel;
    private String remoteAddr;

    /**
     * <p>Constructor for OutboundChannelHandler.</p>
     *
     * @param listener              a {@link ChannelEventListener} object.
     * @param publicExecutor        a {@link ExecutorService} object.
     */
    public OutboundChannelHandler(ChannelEventListener listener, ExecutorService publicExecutor) {
        this.listener = listener;
        this.publicExecutor = publicExecutor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        this.remoteAddr = RemotingUtil.socketAddress2String(channel.remoteAddress());

        sendApiSingleLineCommand(ctx.channel(), "connect")
                .thenAcceptAsync(response -> {
                    log.info("{}", listener);
                    listener.onConnect(
                            new Context(ctx.channel(), OutboundChannelHandler.this),
                            new EslEvent(response, true));
                }, publicExecutor)
                .exceptionally(throwable -> {
                    log.error("Outbound Error", throwable);
                    ctx.channel().close();
                    listener.handleDisconnectNotice(remoteAddr, ctx);
                    return null;
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.debug("channelInactive remoteAddr : {}", remoteAddr);
//        listener.onChannelClosed(remoteAddr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
                log.debug("userEventTriggered remoteAddr : {}, evt state : {} ", remoteAddr, ((IdleStateEvent) evt).state());
                publicExecutor.execute(() -> sendAsyncCommand("bgapi status"));
            }
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught remoteAddr : {}, cause : ", remoteAddr, cause);

        for (final CompletableFuture<EslMessage> apiCall : apiCalls) {
            apiCall.completeExceptionally(cause.getCause());
        }

        for (final CompletableFuture<EslEvent> backgroundJob : backgroundJobs.values()) {
            backgroundJob.completeExceptionally(cause.getCause());
        }
        ctx.channel().close();

        ctx.close();

        ctx.fireExceptionCaught(cause);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EslMessage message) throws Exception {
        final String contentType = message.getContentType();
        log.info("contentType : {}", contentType);
        if (contentType.equals(EslHeaders.Value.TEXT_EVENT_PLAIN) ||
                contentType.equals(EslHeaders.Value.TEXT_EVENT_XML)
        ) {
            //  transform into an event
            final EslEvent eslEvent = new EslEvent(message);
            if (eslEvent.getEventName().equals("BACKGROUND_JOB")) {
                final String backgroundUuid = eslEvent.getEventHeaders().get(EslEventHeaderNames.JOB_UUID);
                final CompletableFuture<EslEvent> future = backgroundJobs.remove(backgroundUuid);
                if (null != future) {
                    future.complete(eslEvent);
                }
            } else {
                listener.handleEslEvent(new Context(ctx.channel(), OutboundChannelHandler.this), eslEvent);
            }
        } else {
            handleEslMessage(ctx, message);
        }
    }

    protected void handleEslMessage(ChannelHandlerContext ctx, EslMessage message) {
        log.info("Received message: [{}]", message);
        final String contentType = message.getContentType();

        switch (contentType) {
            case EslHeaders.Value.API_RESPONSE:
                log.debug("Api response received [{}]", message);
                if (apiCalls.size() > 0) {
                    apiCalls.poll().complete(message);
                }
                break;

            case EslHeaders.Value.COMMAND_REPLY:
                log.debug("Command reply received [{}]", message);
                if (apiCalls.size() > 0) {
                    apiCalls.poll().complete(message);
                }
                break;

            case EslHeaders.Value.AUTH_REQUEST:
                log.error("Auth request received [{}]", message);
                listener.handleAuthRequest(ctx);
                break;

            case EslHeaders.Value.TEXT_DISCONNECT_NOTICE:
                log.error("Disconnect notice received [{}]", message);
                listener.handleDisconnectNotice(remoteAddr, ctx);
                break;

            default:
                log.error("Unexpected message content type [{}]", contentType);
                break;
        }
    }


    /**
     * Synthesise a synchronous command/response by creating a callback object which is placed in
     * queue and blocks waiting for another IO thread to process an incoming {@link EslMessage} and
     * attach it to the callback.
     *
     * @param command single string to send
     * @return the {@link EslMessage} attached to this command's callback
     */
    public EslMessage sendSyncSingleLineCommand(final String command) {
        if (isTraceEnabled) {
            log.trace("sendSyncSingleLineCommand command : {}", command);
        }
        SyncCallback callback = new SyncCallback();
        syncLock.lock();
        try {
            syncCallbacks.add(callback);
            channel.writeAndFlush(command + MESSAGE_TERMINATOR);
        } finally {
            syncLock.unlock();
        }
        //  Block until the response is available
        return callback.get();
    }

    /**
     * Synthesise a synchronous command/response by creating a callback object which is placed in
     * queue and blocks waiting for another IO thread to process an incoming {@link EslMessage} and
     * attach it to the callback.
     *
     * @param commandLines List of command lines to send
     * @return the {@link EslMessage} attached to this command's callback
     */
    public EslMessage sendSyncMultiLineCommand(final List<String> commandLines) {
        SyncCallback callback = new SyncCallback();
        //  Build command with double line terminator at the end
        StringBuilder sb = new StringBuilder();
        for (String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);
        if (isTraceEnabled) {
            log.trace("sendSyncMultiLineCommand command : {}", sb);
        }
        syncLock.lock();
        try {
            syncCallbacks.add(callback);
            channel.writeAndFlush(sb.toString());
        } finally {
            syncLock.unlock();
        }

        //  Block until the response is available
        return callback.get();
    }

    /**
     * Synthesise a synchronous command/response by creating a callback object which is placed in
     * queue and blocks waiting for another IO thread to process an incoming {@link EslMessage} and
     * attach it to the callback.
     *
     * @param channel
     * @param commandLines List of command lines to send
     * @return the {@link EslMessage} attached to this command's callback
     */
    public EslMessage sendSyncMultiLineCommand(Channel channel, final List<String> commandLines) {
        SyncCallback callback = new SyncCallback();
        //  Build command with double line terminator at the end
        StringBuilder sb = new StringBuilder();
        for (String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);

        syncLock.lock();
        try {
            syncCallbacks.add(callback);
            channel.write(sb.toString());
        } finally {
            syncLock.unlock();
        }

        //  Block until the response is available
        return callback.get();
    }

    public void sendAsyncMultiLineCommand(Channel channel, final List<String> commandLines) {
        SyncCallback callback = new SyncCallback();
        //  Build command with double line terminator at the end
        StringBuilder sb = new StringBuilder();
        for (String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);
        syncLock.lock();
        try {
            syncCallbacks.add(callback);
            channel.write(sb.toString());
        } finally {
            syncLock.unlock();
        }
    }

    public CompletableFuture<EslMessage> sendApiSingleLineCommand(Channel channel, final String command) {
        final CompletableFuture<EslMessage> future = new CompletableFuture<>();
        syncLock.lock();
        try {
            channel.writeAndFlush(command + MESSAGE_TERMINATOR);
            apiCalls.add(future);
        } finally {
            syncLock.unlock();
        }
        return future;
    }

    public CompletableFuture<EslEvent> sendBackgroundApiCommand(Channel channel, final String command) {
        return sendApiSingleLineCommand(channel, command)
                .thenComposeAsync(result -> {
                    if (result.hasHeader(EslHeaders.Name.JOB_UUID)) {
                        final String jobId = result.getHeaderValue(EslHeaders.Name.JOB_UUID);
                        final CompletableFuture<EslEvent> resultFuture = new CompletableFuture<>();
                        backgroundJobs.put(jobId, resultFuture);
                        return resultFuture;
                    } else {
                        final CompletableFuture<EslEvent> resultFuture = new CompletableFuture<>();
                        resultFuture.completeExceptionally(new IllegalStateException("Missing Job-UUID header in bgapi response"));
                        return resultFuture;
                    }
                }, backgroundJobExecutor);
    }

    public CompletableFuture<EslMessage> sendApiMultiLineCommand(Channel channel, final List<String> commandLines) {
        //  Build command with double line terminator at the end
        final StringBuilder sb = new StringBuilder();
        for (final String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);
        syncLock.lock();
        try {
            final CompletableFuture<EslMessage> future = new CompletableFuture<>();
            channel.write(sb.toString());
            channel.flush();
            return future;
        } finally {
            syncLock.unlock();
        }
    }

    /**
     * Returns the Job UUID of that the response event will have.
     *
     * @param command cmd
     * @return Job-UUID as a string
     */
    public String sendAsyncCommand(final String command) {
        /*
         * Send synchronously to get the Job-UUID to return, the results of the actual
         * job request will be returned by the server as an async event.
         */
        EslMessage response = sendSyncSingleLineCommand(command);
        if (isTraceEnabled) {
            log.trace("sendAsyncCommand command : {}, response : {}", command, response);
        }
        if (response.hasHeader(EslHeaders.Name.JOB_UUID)) {
            return response.getHeaderValue(EslHeaders.Name.JOB_UUID);
        } else {
            log.warn("sendAsyncCommand command : {}, response : {}", command, EslHelper.formatEslMessage(response));
            throw new IllegalStateException("Missing Job-UUID header in bgapi response");
        }
    }

    /**
     * <p>close.</p>
     *
     * @return a {@link ChannelFuture} object.
     */
    public ChannelFuture close() {
        return channel.close();
    }

    class SyncCallback {
        private final CountDownLatch latch = new CountDownLatch(1);
        private EslMessage response;

        /**
         * Block waiting for the countdown latch to be released, then return the
         * associated response object.
         *
         * @return msg
         */
        EslMessage get() {
            try {
                log.trace("awaiting latch ... ");
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            log.trace("returning response [{}]", response);
            return response;
        }

        /**
         * Attach this response to the callback and release the countdown latch.
         *
         * @param response res
         */
        void handle(EslMessage response) {
            this.response = response;
            log.trace("releasing latch for response [{}]", response);
            latch.countDown();
        }
    }


}
