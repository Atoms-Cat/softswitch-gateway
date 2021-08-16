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

package link.thingscloud.opensips.event.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.timeout.IdleStateEvent;
import link.thingscloud.opensips.event.listener.ChannelEventListener;
import link.thingscloud.opensips.util.RemotingUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>OutboundChannelHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Slf4j
public class EventChannelHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String MESSAGE_TERMINATOR = "\n\n";
    private static final String LINE_TERMINATOR = "\n";

    private final Lock syncLock = new ReentrantLock();
    private final ChannelEventListener listener;
    private final ExecutorService publicExecutor;

    private Channel channel;
    private String remoteAddr;

    /**
     * <p>Constructor for OutboundChannelHandler.</p>
     *
     * @param listener       a {@link ChannelEventListener} object.
     * @param publicExecutor a {@link ExecutorService} object.
     */
    public EventChannelHandler(ChannelEventListener listener, ExecutorService publicExecutor) {
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
        if (channel != null && channel.remoteAddress() != null) {
            this.remoteAddr = RemotingUtil.socketAddress2String(channel.remoteAddress());
        }

        sendApiSingleLineCommand(ctx.channel(), "connect")
                .thenAcceptAsync(response -> {
                    log.info("{}", listener);
                    listener.onConnect(new Context(ctx.channel(), EventChannelHandler.this), response);
                }, publicExecutor)
                .exceptionally(throwable -> {
                    log.error("Opensips Event Error", throwable);
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
        log.info("channelInactive remoteAddr : {}", remoteAddr);
//        listener.onChannelClosed(remoteAddr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.error("userEventTriggered remoteAddr : {}, evt state : {} ", remoteAddr, ((IdleStateEvent) evt).state());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught remoteAddr : {}, cause : ", remoteAddr, cause);
        ctx.channel().close();
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead remoteAddr : {}, msg : {} ", remoteAddr, msg);
        if (msg instanceof DatagramPacket) {
            channelRead0(ctx, (DatagramPacket) msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        this.channel = ctx.channel();
        if (channel != null && channel.remoteAddress() != null) {
            this.remoteAddr = RemotingUtil.socketAddress2String(channel.remoteAddress());
        } else {
            this.remoteAddr = msg.sender().getAddress().getHostAddress() + ":" + msg.sender().getPort();
        }
        log.info("channelRead0 remoteAddr : {}, msg : {}", remoteAddr, msg);
        listener.handleEvent(new Context(ctx.channel(), EventChannelHandler.this), msg);
    }

    public CompletableFuture<DatagramPacket> sendApiSingleLineCommand(Channel channel, final String command) {
        final CompletableFuture<DatagramPacket> future = new CompletableFuture<>();
        syncLock.lock();
        try {
            channel.writeAndFlush(command + MESSAGE_TERMINATOR);
        } finally {
            syncLock.unlock();
        }
        return future;
    }

    /**
     * <p>close.</p>
     *
     * @return a {@link ChannelFuture} object.
     */
    public ChannelFuture close() {
        return channel.close();
    }


}
