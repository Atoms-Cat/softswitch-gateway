package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.handler.Context;

/**
 * @author th158
 */
public class DefaultClientConnectHandler extends AbstractClientConnectHandler {


    @Override
    public void onConnect(Context context, Object msg) {
        log.debug("Default Client connect handler event[{}]", msg);
    }
}
