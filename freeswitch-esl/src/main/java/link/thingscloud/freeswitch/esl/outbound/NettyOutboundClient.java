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

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import link.thingscloud.freeswitch.esl.outbound.option.ConnectState;
import link.thingscloud.freeswitch.esl.outbound.option.OutboundClientOption;
import link.thingscloud.freeswitch.esl.outbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.util.StringUtils;


/**
 * <p>NettyInboundClient class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public class NettyOutboundClient extends AbstractOutboundClientCommand {

    private Channel channel;

    /**
     * <p>Constructor for NettyInboundClient.</p>
     *
     * @param option a {@link OutboundClientOption} object.
     */
    public NettyOutboundClient(OutboundClientOption option) {
        super(option);
    }

    private ChannelFuture doBind(final ServerOption serverOption) {
        log.info("start bind server [{}:{}] ...", serverOption.host(), serverOption.port());
        serverOption.addConnectTimes();
        serverOption.state(ConnectState.CONNECTING);

        ChannelFuture channelFuture;
        if (StringUtils.isBlank(serverOption.host())) {
            channelFuture = bootstrap.bind(serverOption.port()).syncUninterruptibly();
        } else {
            channelFuture = bootstrap.bind(serverOption.host(), serverOption.port()).syncUninterruptibly();
        }
        if (channelFuture != null && channelFuture.isSuccess()) {
            //获取通道
            channel = channelFuture.channel();
            notifyStarted();
            log.info("outbound client server start success, listen port on {}", serverOption.port());
        } else {
            log.info("outbound client server start fail");
        }
        return channelFuture;
    }

    @Override
    protected void doStart() {
        log.info("outbound client will start ...");
        if (option != null) {
            ServerOption serverOption = option.serverOption();
            if (serverOption.state() == ConnectState.INIT) {
                serverOption.state(ConnectState.CONNECTING);
                doBind(serverOption);
            }
        }
    }

    @Override
    protected void doStop() {
        log.info("outbound client will shutdown ...");
        channel.close();
        workerGroup.shutdownGracefully();
        parentGroup.shutdownGracefully();
        notifyStopped();
    }
}
