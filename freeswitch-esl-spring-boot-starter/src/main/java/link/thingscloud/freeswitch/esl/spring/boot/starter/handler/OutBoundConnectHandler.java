package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

public interface OutBoundConnectHandler {
    /**
     * @param context
     * @param eslEvent
     */
    void onConnect(Context context, EslEvent eslEvent);
}
