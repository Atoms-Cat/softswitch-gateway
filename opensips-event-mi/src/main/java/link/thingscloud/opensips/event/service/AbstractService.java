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

package link.thingscloud.opensips.event.service;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import link.thingscloud.opensips.event.EventClient;
import link.thingscloud.opensips.event.handler.Context;
import link.thingscloud.opensips.event.handler.EventChannelHandler;
import link.thingscloud.opensips.event.option.EventClientOption;

import java.util.HashMap;
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
abstract class AbstractService extends AbstractNettyService implements EventClient {

    private final ScheduledThreadPoolExecutor scheduledPoolExecutor = new ScheduledThreadPoolExecutor(1,
            new DefaultThreadFactory("outbound-scheduled-pool", true));

    private final Map<String, EventChannelHandler> handlerTable = new HashMap<>(32);

    AbstractService(EventClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventClientOption option() {
        return option;
    }

    @Override
    public Bootstrap bootstrap() {
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
    public void handleDisconnectNotice(String address, ChannelHandlerContext ctx) {
        log.info("Disconnected[{}] ...", address);
        ctx.channel().close();
        ctx.close();
    }


    @Override
    public void onConnect(Context context, Object msg) {
        this.option().listeners().forEach(listener -> listener.onConnect(context, msg));
    }

    @Override
    public void handleEvent(Context context, Object msg) {
        this.option().listeners().forEach(listener -> listener.handleEvent(context, msg));

    }
    
}
