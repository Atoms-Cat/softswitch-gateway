package com.atomscat.freeswitch.esl.outbound;

import com.atomscat.freeswitch.esl.outbound.option.OutboundClientOption;

/**
 * @author everyone
 * @since 1.6.0
 */
abstract class AbstractOutboundClientCommand extends AbstractOutboundClient {
    AbstractOutboundClientCommand(OutboundClientOption option) {
        super(option);
    }
}
