package com.atomscat.opensips.event.service;

import com.atomscat.opensips.event.option.EventClientOption;
import com.atomscat.opensips.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import com.atomscat.opensips.event.option.ConnectState;
import com.atomscat.opensips.event.option.ServerOption;

/**
 * @author th158
 */
public class NettyEventService extends AbstractServiceCommand {
    private Channel channel;

    public NettyEventService(EventClientOption option) {
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
            log.info("opensips event server start success, listen port on {}", serverOption.port());
        } else {
            log.info("opensips event server start fail");
        }
        return channelFuture;
    }


    @Override
    protected void doStart() {
        log.info("opensips client will start ...");
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
        log.info("opensips client will shutdown ...");
        channel.close();
        workerGroup.shutdownGracefully();
        notifyStopped();
    }
}
