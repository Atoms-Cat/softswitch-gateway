package com.atomscat.freeswitch.esl.outbound;

import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;

/**
 * @author everyone
 * @since 1.6.0
 */
abstract class AbstractOutboundServerCommand extends AbstractOutboundServer {
    AbstractOutboundServerCommand(OutboundServerOption option) {
        super(option);
    }
}
