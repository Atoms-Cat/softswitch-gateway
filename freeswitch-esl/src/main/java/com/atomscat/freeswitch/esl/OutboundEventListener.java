package com.atomscat.freeswitch.esl;

import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

public interface OutboundEventListener {

    void handleEslEvent(Context context, EslEvent event);

    void onConnect(Context context, EslEvent eslEvent);

}
