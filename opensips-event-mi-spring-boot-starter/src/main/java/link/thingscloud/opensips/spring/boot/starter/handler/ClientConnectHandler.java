package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.handler.Context;

public interface ClientConnectHandler {
    /**
     * @param context a {@link Context} object.
     * @param msg a {@link Object} object.
     */
    void onConnect(Context context, Object msg);
}
