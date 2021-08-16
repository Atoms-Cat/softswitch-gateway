package link.thingscloud.opensips.spring.boot.starter.template;

import io.netty.channel.socket.DatagramPacket;
import link.thingscloud.opensips.event.handler.Context;
import link.thingscloud.opensips.event.listener.ServerEventListener;
import link.thingscloud.opensips.spring.boot.starter.handler.ClientConnectHandler;
import link.thingscloud.opensips.spring.boot.starter.handler.ClientEventHandler;
import link.thingscloud.opensips.spring.boot.starter.handler.DefaultClientConnectHandler;
import link.thingscloud.opensips.spring.boot.starter.handler.DefaultClientEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author th158
 */
@Slf4j
public class ServerEventListenerTemplate implements ServerEventListener, InitializingBean {

    @Autowired
    private final List<ClientConnectHandler> clientConnectHandlers = Collections.emptyList();

    @Autowired
    private final List<ClientEventHandler> clientEventHandlers = Collections.emptyList();

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
