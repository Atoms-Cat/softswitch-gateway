package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

public interface OutBoundConnectHandler {
    /**
     * @param context  a {@link Context} object.
     * @param eslEvent a {@link EslEvent} object.
     */
    void onConnect(Context context, EslEvent eslEvent);
}
