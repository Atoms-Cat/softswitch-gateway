package link.thingscloud.freeswitch.esl;

import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

public interface OutboundEventListener {

    void handleEslEvent(Context context, EslEvent event);

    void onConnect(Context context, EslEvent eslEvent);

}
