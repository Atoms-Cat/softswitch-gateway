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

package link.thingscloud.freeswitch.esl.outbound;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import link.thingscloud.freeswitch.esl.OutboundClient;
import link.thingscloud.freeswitch.esl.exception.InboundClientException;
import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.outbound.handler.OutboundChannelHandler;
import link.thingscloud.freeswitch.esl.outbound.option.ConnectState;
import link.thingscloud.freeswitch.esl.outbound.option.OutboundClientOption;
import link.thingscloud.freeswitch.esl.outbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p>flow :</p>
 * |------------------------------------|
 * |                                    |
 * \|            |---» CONNECTED  ---» CLOSED  ---» SHUTDOWN
 * INIT ----» CONNECTING -----|
 * /|            |---» FAILED
 * |                     |
 * ----------------------|
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
abstract class AbstractOutboundClient extends AbstractNettyOutboundClient implements OutboundClient {

    private final ScheduledThreadPoolExecutor scheduledPoolExecutor = new ScheduledThreadPoolExecutor(1,
            new DefaultThreadFactory("outbound-scheduled-pool", true));

    private final Map<String, OutboundChannelHandler> handlerTable = new HashMap<>(32);

    AbstractOutboundClient(OutboundClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutboundClientOption option() {
        return option;
    }

    @Override
    public ServerBootstrap bootstrap() {
        return bootstrap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleAuthRequest(ChannelHandlerContext ctx) {
        log.info("Auth requested[{}], sending [auth {}]", ctx, "*****");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEslEvent(Context context, EslEvent eslEvent) {
        this.option().listeners().forEach(listener -> listener.handleEslEvent(context, eslEvent));
    }

    @Override
    public void onConnect(Context context, EslEvent eslEvent) {
        this.option().listeners().forEach(listener -> listener.onConnect(context, eslEvent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDisconnectNotice(String address, ChannelHandlerContext ctx) {
        log.info("Disconnected[{}] ...", address);
        ctx.channel().close();
        ctx.close();
    }




}
