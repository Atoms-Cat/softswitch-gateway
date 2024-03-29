package com.atomscat.opensips.spring.boot.starter.template;

import com.atomscat.opensips.event.handler.Context;
import com.atomscat.opensips.event.listener.ServerEventListener;
import com.atomscat.opensips.spring.boot.starter.handler.ClientConnectHandler;
import com.atomscat.opensips.spring.boot.starter.handler.ClientEventHandler;
import com.atomscat.opensips.spring.boot.starter.handler.DefaultClientConnectHandler;
import com.atomscat.opensips.spring.boot.starter.handler.DefaultClientEventHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author th158
 */
@Slf4j
@RequiredArgsConstructor
public class ServerEventListenerTemplate implements ServerEventListener, InitializingBean {

    private final List<ClientConnectHandler> clientConnectHandlers;

    private final List<ClientEventHandler> clientEventHandlers;

    private final ClientConnectHandler defaultClientConnectHandler = new DefaultClientConnectHandler();
    private final ClientEventHandler defaultClientEventHandler = new DefaultClientEventHandler();

    @Override
    public void handleEvent(Context context, Object msg) {
        if (msg instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket) msg;
        }
        if (!CollectionUtils.isEmpty(clientEventHandlers)) {
            clientEventHandlers.forEach(handler -> handler.handler(context, msg));
            return;
        }
        defaultClientEventHandler.handler(context, msg);
    }

    @Override
    public void onConnect(Context context, Object msg) {
        if (!CollectionUtils.isEmpty(clientConnectHandlers)) {
            clientConnectHandlers.forEach(handler -> handler.onConnect(context, msg));
            return;
        }
        defaultClientConnectHandler.onConnect(context, msg);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Opensips Client Event Listener init ...");
    }

}
