package com.atomscat.freeswitch.esl.spring.boot.starter.handler;


import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

/**
 *
 */
public interface OutBoundEventHandler {

    /**
     * @param context a {@link Context} object.
     * @param eslEvent a {@link EslEvent} object.
     */
    void handler(Context context, EslEvent eslEvent);
}
