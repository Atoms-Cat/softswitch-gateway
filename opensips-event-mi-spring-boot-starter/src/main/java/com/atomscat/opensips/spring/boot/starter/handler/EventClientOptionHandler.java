package com.atomscat.opensips.spring.boot.starter.handler;


import com.atomscat.opensips.event.option.EventClientOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public interface EventClientOptionHandler {

    /**
     * <p>getOption.</p>
     *
     * @return a {@link EventClientOption} object.
     */
    EventClientOption getOption();

}
