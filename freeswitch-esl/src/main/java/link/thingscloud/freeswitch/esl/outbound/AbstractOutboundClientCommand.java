package link.thingscloud.freeswitch.esl.outbound;

import link.thingscloud.freeswitch.esl.outbound.option.OutboundClientOption;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
abstract class AbstractOutboundClientCommand extends AbstractOutboundClient {
    AbstractOutboundClientCommand(OutboundClientOption option) {
        super(option);
    }
}
