package com.atomscat.opensips.spring.boot.starter.handler;


import com.atomscat.opensips.event.handler.Context;

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
