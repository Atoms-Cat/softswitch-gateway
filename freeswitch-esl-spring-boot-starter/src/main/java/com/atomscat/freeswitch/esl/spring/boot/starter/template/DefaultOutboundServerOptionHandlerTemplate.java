package com.atomscat.freeswitch.esl.spring.boot.starter.template;

import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.AbstractOutboundServerOptionHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundServerProperties;

/**
 * <p>DefaultInboundClientOptionHandlerTemplate class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public class DefaultOutboundServerOptionHandlerTemplate extends AbstractOutboundServerOptionHandler {

    public DefaultOutboundServerOptionHandlerTemplate(OutboundServerProperties properties) {
        super(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(OutboundServerOption inboundClientOption) {
    }

}
