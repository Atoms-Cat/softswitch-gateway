package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.handler.Context;

/**
 * @author th158
 */
public class DefaultClientEventHandler extends AbstractClientEventHandler {

    @Override
    public void handler(Context context, Object msg) {
        log.debug("Default Client Event handler event[{}]", msg);
    }
}
