package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.outbound.option.OutboundClientOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public interface OutboundClientOptionHandler {

    /**
     * <p>getOption.</p>
     *
     * @return a {@link OutboundClientOption} object.
     */
    OutboundClientOption getOption();

}
