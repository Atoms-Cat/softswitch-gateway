package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.inbound.option.ServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.InboundClientProperties;
import com.atomscat.freeswitch.esl.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author everyone
 * @version 1.0.0
 */
public abstract class AbstractInboundClientOptionHandler implements InboundClientOptionHandler {

    @Autowired
    protected InboundClientProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param inboundClientOption a {@link InboundClientOption} object.
     */
    protected abstract void intercept(InboundClientOption inboundClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public InboundClientOption getOption() {
        InboundClientOption option = newInboundClientOption();
        properties.getServers().forEach(server -> {
            if (StringUtils.isNotBlank(server.getHost()) && server.getPort() > 1) {
                option.addServerOption(new ServerOption(server.getHost(), server.getPort())
                        .timeoutSeconds(server.getTimeoutSeconds())
                        .password(server.getPassword())
                        .routingKey(server.getRoutingKey()));
            }
        });
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
     * @return a {@link InboundClientOption} object.
     */
    protected InboundClientOption newInboundClientOption() {
        return new InboundClientOption().sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .publicExecutorThread(properties.getPublicExecutorThread())
                .privateExecutorThread(properties.getPrivateExecutorThread())
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
