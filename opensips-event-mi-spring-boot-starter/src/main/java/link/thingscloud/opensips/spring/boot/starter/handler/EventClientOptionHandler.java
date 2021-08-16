package link.thingscloud.opensips.spring.boot.starter.handler;


import link.thingscloud.opensips.event.option.EventClientOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author zhouhailin
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
