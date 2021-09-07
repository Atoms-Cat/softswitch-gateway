package com.atomscat.freeswitch.esl.spring.boot.starter.handler;


import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

public class DefaultOutBoundEventHandler extends AbstractOutBoundEventHandler {
    @Override
    public void handler(Context context, EslEvent eslEvent) {
        log.debug("Default outbound connect handler event[{}]", EslHelper.formatEslEvent(eslEvent));
    }
}
