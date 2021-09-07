package com.atomscat.opensips.event.service;


import com.atomscat.opensips.event.option.EventClientOption;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
abstract class AbstractServiceCommand extends AbstractService {

    AbstractServiceCommand(EventClientOption option) {
        super(option);
    }
}
