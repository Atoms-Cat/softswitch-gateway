package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;


import link.thingscloud.freeswitch.esl.helper.EslHelper;
import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

public class DefaultOutBoundEventHandler extends AbstractOutBoundEventHandler {
    @Override
    public void handler(Context context, EslEvent eslEvent) {
        log.debug("Default outbound connect handler event[{}]", EslHelper.formatEslEvent(eslEvent));
    }
}
