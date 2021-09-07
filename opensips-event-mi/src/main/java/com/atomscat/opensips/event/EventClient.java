package com.atomscat.opensips.event;


import com.atomscat.opensips.event.option.EventClientOption;
import io.netty.bootstrap.Bootstrap;

/**
 * @author th158
 */
public interface EventClient extends EventClientService, EventClientCommand {
    /**
     * <p>newInstance.</p>
     *
     * @param option a {@link EventClientOption} object.
     * @return a {@link EventClient} object.
     */
    static EventClient newInstance(EventClientOption option) {
        return EventClientFactory.getInstance().newEventClient(option);
    }

    /**
     * 获取客户可配置选项
     *
     * @return this
     */
    EventClientOption option();

    /**
     * 获取 netty bootstrap
     *
     * @return a {@link Bootstrap} object.
     */
    Bootstrap bootstrap();
}
