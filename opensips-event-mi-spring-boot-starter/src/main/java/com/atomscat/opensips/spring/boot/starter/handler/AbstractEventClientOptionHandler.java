package com.atomscat.opensips.spring.boot.starter.handler;

import com.atomscat.opensips.event.option.EventClientOption;
import com.atomscat.opensips.event.option.ServerOption;
import com.atomscat.opensips.spring.boot.starter.propeties.OpensipsEventProperties;
import com.atomscat.opensips.spring.boot.starter.propeties.ServerProperties;
import com.atomscat.opensips.util.StringUtils;
import lombok.RequiredArgsConstructor;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
@RequiredArgsConstructor
public abstract class AbstractEventClientOptionHandler implements EventClientOptionHandler {

    private final OpensipsEventProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param eventClientOption a {@link EventClientOption} object.
     */
    protected abstract void intercept(EventClientOption eventClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public EventClientOption getOption() {
        EventClientOption option = newOutboundClientOption();
        ServerProperties server = properties.getServer();
        if (StringUtils.isNotBlank(server.getHost()) && server.getPort() > 1) {
            option.addServerOption(new ServerOption(server.getHost(), server.getPort()).timeoutSeconds(server.getTimeoutSeconds()).password(server.getPassword()));
        }

        properties.getEvents().forEach(event -> {
            if (StringUtils.isNotBlank(event)) {
                option.addEvents(event);
            }
        });
        intercept(option);
        return option;
    }

    /**
     * <p>newInboundClientOption.</p>
     *
     * @return a {@link EventClientOption} object.
     */
    protected EventClientOption newOutboundClientOption() {
        return new EventClientOption().sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .callbackExecutorThread(properties.getCallbackExecutorThread())
                .defaultTimeoutSeconds(properties.getDefaultTimeoutSeconds())
                .readTimeoutSeconds(properties.getReadTimeoutSeconds())
                .readerIdleTimeSeconds(properties.getReaderIdleTimeSeconds())
                .defaultPassword(properties.getDefaultPassword())
                .disablePublicExecutor(properties.isDisablePublicExecutor())
                .performance(properties.isPerformance())
                .performanceCostTime(properties.getPerformanceCostTime())
                .eventPerformance(properties.isEventPerformance())
                .eventPerformanceCostTime(properties.getEventPerformanceCostTime());
    }

}
