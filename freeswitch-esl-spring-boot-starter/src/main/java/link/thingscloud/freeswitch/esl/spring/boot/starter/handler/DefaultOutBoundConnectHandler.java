package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import link.thingscloud.freeswitch.esl.helper.EslHelper;
import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * @author th158
 */
public class DefaultOutBoundConnectHandler extends AbstractOutBoundConnectHandler {
    @Override
    public void onConnect(Context context, EslEvent eslEvent) {
        log.debug("Default outbound connect handler event[{}]", EslHelper.formatEslEvent(eslEvent));
    }
}
