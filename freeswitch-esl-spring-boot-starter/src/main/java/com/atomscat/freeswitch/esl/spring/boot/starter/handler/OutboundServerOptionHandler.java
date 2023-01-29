package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public interface OutboundServerOptionHandler {

    /**
     * <p>getOption.</p>
     *
     * @return a {@link OutboundServerOption} object.
     */
    OutboundServerOption getOption();

}
