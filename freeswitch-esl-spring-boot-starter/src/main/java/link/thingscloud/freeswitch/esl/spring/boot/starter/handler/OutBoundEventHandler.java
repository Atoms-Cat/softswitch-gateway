package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;


import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

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
