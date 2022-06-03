package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public interface InboundClientOptionHandler {

    /**
     * <p>getOption.</p>
     *
     * @return a {@link InboundClientOption} object.
     */
    InboundClientOption getOption();

}
