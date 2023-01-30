package com.atomscat.freeswitch.esl.spring.boot.starter.template;

import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.AbstractInboundClientOptionHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.InboundClientProperties;

/**
 * <p>DefaultInboundClientOptionHandlerTemplate class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public class DefaultInboundClientOptionHandlerTemplate extends AbstractInboundClientOptionHandler {

    public DefaultInboundClientOptionHandlerTemplate(InboundClientProperties properties) {
        super(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(InboundClientOption inboundClientOption) {
    }

}
