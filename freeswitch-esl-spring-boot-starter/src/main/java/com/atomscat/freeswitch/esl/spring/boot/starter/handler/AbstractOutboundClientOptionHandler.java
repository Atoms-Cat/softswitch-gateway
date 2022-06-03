package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.outbound.option.OutboundClientOption;
import com.atomscat.freeswitch.esl.outbound.option.ServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.ServerProperties;
import com.atomscat.freeswitch.esl.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public abstract class AbstractOutboundClientOptionHandler implements OutboundClientOptionHandler {

    @Autowired
    protected OutboundClientProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param inboundClientOption a {@link OutboundClientOption} object.
     */
    protected abstract void intercept(OutboundClientOption inboundClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public OutboundClientOption getOption() {
        OutboundClientOption option = newOutboundClientOption();
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
     * @return a {@link OutboundClientOption} object.
     */
    protected OutboundClientOption newOutboundClientOption() {
        return new OutboundClientOption().sndBufSize(properties.getSndBufSize())
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
