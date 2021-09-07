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

package com.atomscat.freeswitch.esl.inbound;

import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.util.StringUtils;
import com.atomscat.freeswitch.esl.constant.EslConstant;
import com.atomscat.freeswitch.esl.exception.InboundTimeoutExcetion;
import com.atomscat.freeswitch.esl.inbound.handler.InboundChannelHandler;
import com.atomscat.freeswitch.esl.transport.CommandResponse;
import com.atomscat.freeswitch.esl.transport.SendEvent;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.message.EslMessage;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/**
 * <p>NettyInboundClient class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public class NettyInboundClient extends AbstractInboundClientCommand {

    /**
     * <p>Constructor for NettyInboundClient.</p>
     *
     * @param option a {@link InboundClientOption} object.
     */
    public NettyInboundClient(InboundClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EslMessage sendSyncApiCommand(String address, String command, String arg) {
        InboundChannelHandler handler = getAuthedHandler(address);
        StringBuilder sb = new StringBuilder();
        if (command != null && !command.isEmpty()) {
            sb.append("api ");
            sb.append(command);
        }
        if (arg != null && !arg.isEmpty()) {
            sb.append(' ');
            sb.append(arg);
        }
        log.debug("sendSyncApiCommand address : {}, command : {}, arg : {}", address, command, arg);
        return handler.sendSyncSingleLineCommand(sb.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EslMessage sendSyncApiCommand(String address, String command, String arg, long timeoutSeconds) throws InboundTimeoutExcetion {
        try {
            return publicExecutor.submit(() -> sendSyncApiCommand(address, command, arg)).get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new InboundTimeoutExcetion(String.format("sendSyncApiCommand address : %s, command : %s, arg : %s, timeoutSeconds : %s", address, command, arg, timeoutSeconds), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSyncApiCommand(String address, String command, String arg, Consumer<EslMessage> consumer) {
        publicExecutor.execute(() -> {
            EslMessage msg = sendSyncApiCommand(address, command, arg);
            if (consumer != null) {
                consumer.accept(msg);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendAsyncApiCommand(String address, String command, String arg) {
        InboundChannelHandler handler = getAuthedHandler(address);
        StringBuilder sb = new StringBuilder();
        if (command != null && !command.isEmpty()) {
            sb.append("bgapi ");
            sb.append(command);
        }
        if (arg != null && !arg.isEmpty()) {
            sb.append(' ');
            sb.append(arg);
        }
        return handler.sendAsyncCommand(sb.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAsyncApiCommand(String address, String command, String arg, Consumer<String> consumer) {
        publicExecutor.execute(() -> {
            String msg = sendAsyncApiCommand(address, command, arg);
            if (consumer != null) {
                consumer.accept(msg);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse setEventSubscriptions(String address, String format, String events) {
        if (!StringUtils.equals(format, EslConstant.PLAIN)) {
            throw new IllegalStateException("Only 'plain' event format is supported at present");
        }
        InboundChannelHandler handler = getAuthedHandler(address);

        StringBuilder sb = new StringBuilder();
        sb.append("event ");
        sb.append(format);
        if (events != null && !events.isEmpty()) {
            sb.append(' ');
            sb.append(events);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse cancelEventSubscriptions(String address) {
        InboundChannelHandler handler = getAuthedHandler(address);
        EslMessage response = handler.sendSyncSingleLineCommand("noevents");
        return new CommandResponse("noevents", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse addEventFilter(String address, String eventHeader, String valueToFilter) {
        InboundChannelHandler handler = getAuthedHandler(address);
        StringBuilder sb = new StringBuilder();
        if (eventHeader != null && !eventHeader.isEmpty()) {
            sb.append("filter ");
            sb.append(eventHeader);
        }
        if (valueToFilter != null && !valueToFilter.isEmpty()) {
            sb.append(' ');
            sb.append(valueToFilter);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());

        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse deleteEventFilter(String address, String eventHeader, String valueToFilter) {
        InboundChannelHandler handler = getAuthedHandler(address);

        StringBuilder sb = new StringBuilder();
        if (eventHeader != null && !eventHeader.isEmpty()) {
            sb.append("filter delete ");
            sb.append(eventHeader);
        }
        if (valueToFilter != null && !valueToFilter.isEmpty()) {
            sb.append(' ');
            sb.append(valueToFilter);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse sendEvent(String address, SendEvent sendEvent) {
        InboundChannelHandler handler = getAuthedHandler(address);
        EslMessage response = handler.sendSyncMultiLineCommand(sendEvent.getMsgLines());
        return new CommandResponse(sendEvent.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEvent(String address, SendEvent sendEvent, Consumer<CommandResponse> consumer) {
        publicExecutor.execute(() -> {
            CommandResponse response = sendEvent(address, sendEvent);
            if (consumer != null) {
                consumer.accept(response);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse sendMessage(String address, SendMsg sendMsg) {
        InboundChannelHandler handler = getAuthedHandler(address);
        EslMessage response = handler.sendSyncMultiLineCommand(sendMsg.getMsgLines());
        return new CommandResponse(sendMsg.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(String address, SendMsg sendMsg, Consumer<CommandResponse> consumer) {
        publicExecutor.execute(() -> {
            CommandResponse response = sendMessage(address, sendMsg);
            if (consumer != null) {
                consumer.accept(response);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse setLoggingLevel(String address, String level) {
        InboundChannelHandler handler = getAuthedHandler(address);
        StringBuilder sb = new StringBuilder();
        if (level != null && !level.isEmpty()) {
            sb.append("log ");
            sb.append(level);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse cancelLogging(String address) {
        InboundChannelHandler handler = getAuthedHandler(address);
        EslMessage response = handler.sendSyncSingleLineCommand("nolog");
        return new CommandResponse("nolog", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse close(String address) {
        InboundChannelHandler handler = getAuthedHandler(address);
        EslMessage response = handler.sendSyncSingleLineCommand("exit");
        return new CommandResponse("exit", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InboundClient closeChannel(String address) {
        getAuthedHandler(address).close();
        return this;
    }
}
