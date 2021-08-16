package link.thingscloud.opensips.event.service;


import link.thingscloud.opensips.event.option.EventClientOption;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
abstract class AbstractServiceCommand extends AbstractService {

    AbstractServiceCommand(EventClientOption option) {
        super(option);
    }
}
