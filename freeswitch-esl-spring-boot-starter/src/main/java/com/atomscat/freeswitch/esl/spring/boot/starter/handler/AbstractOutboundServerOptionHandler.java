package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;
import com.atomscat.freeswitch.esl.outbound.option.ServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundServerProperties;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.ServerProperties;
import com.atomscat.freeswitch.esl.util.StringUtils;
import lombok.RequiredArgsConstructor;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
@RequiredArgsConstructor
public abstract class AbstractOutboundServerOptionHandler implements OutboundServerOptionHandler {

    private final OutboundServerProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param inboundClientOption a {@link OutboundServerOption} object.
     */
    protected abstract void intercept(OutboundServerOption inboundClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public OutboundServerOption getOption() {
        OutboundServerOption option = newOutboundServerOption();
        ServerProperties server = properties.getServer();
        if (StringUtils.isNotBlank(server.getHost()) && server.getPort() > 1) {
            option.addServerOption(new ServerOption(server.getHost(), server.getPort())
                    .timeoutSeconds(server.getTimeoutSeconds())
                    .password(server.getPassword())
                    .routingKey(server.getRoutingKey()));
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
     * @return a {@link OutboundServerOption} object.
     */
    protected OutboundServerOption newOutboundServerOption() {
        return new OutboundServerOption().sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .publicExecutorThread(properties.getPublicExecutorThread())
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
