package com.atomscat.opensips.spring.boot.starter.handler;


import com.atomscat.opensips.event.handler.Context;

/**
 * @author th158
 */
public class DefaultClientConnectHandler extends AbstractClientConnectHandler {


    @Override
    public void onConnect(Context context, Object msg) {
        log.debug("Default Client connect handler event[{}]", msg);
    }
}
