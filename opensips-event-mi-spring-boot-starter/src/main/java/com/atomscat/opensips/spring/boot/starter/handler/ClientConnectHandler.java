package com.atomscat.opensips.spring.boot.starter.handler;


import com.atomscat.opensips.event.handler.Context;

public interface ClientConnectHandler {
    /**
     * @param context a {@link Context} object.
     * @param msg     a {@link Object} object.
     */
    void onConnect(Context context, Object msg);
}
