package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.handler.Context;

/**
 *
 */
public interface ClientEventHandler {

    /**
     * @param context
     * @param msg
     */
    void handler(Context context, Object msg);
}
