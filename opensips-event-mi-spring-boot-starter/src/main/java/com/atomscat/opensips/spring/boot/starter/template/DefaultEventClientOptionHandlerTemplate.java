package com.atomscat.opensips.spring.boot.starter.template;

import com.atomscat.opensips.event.option.EventClientOption;
import com.atomscat.opensips.spring.boot.starter.handler.AbstractEventClientOptionHandler;
import com.atomscat.opensips.spring.boot.starter.propeties.OpensipsEventProperties;

/**
 * <p>DefaultInboundClientOptionHandlerTemplate class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public class DefaultEventClientOptionHandlerTemplate extends AbstractEventClientOptionHandler {

    public DefaultEventClientOptionHandlerTemplate(OpensipsEventProperties properties) {
        super(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(EventClientOption eventClientOption) {
    }

}
