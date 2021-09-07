package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

/**
 * @author th158
 */
public class DefaultOutBoundConnectHandler extends AbstractOutBoundConnectHandler {
    @Override
    public void onConnect(Context context, EslEvent eslEvent) {
        log.debug("Default outbound connect handler event[{}]", EslHelper.formatEslEvent(eslEvent));
    }
}
