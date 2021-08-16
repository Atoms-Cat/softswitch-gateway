package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.handler.Context;

public interface ClientConnectHandler {
    /**
     * @param context
     * @param msg
     */
    void onConnect(Context context, Object msg);
}
