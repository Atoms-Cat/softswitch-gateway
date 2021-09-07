package com.atomscat.opensips.spring.boot.starter.handler;


import com.atomscat.opensips.event.handler.Context;

/**
 * @author th158
 */
public class DefaultClientEventHandler extends AbstractClientEventHandler {

    @Override
    public void handler(Context context, Object msg) {
        log.debug("Default Client Event handler event[{}]", msg);
    }
}
