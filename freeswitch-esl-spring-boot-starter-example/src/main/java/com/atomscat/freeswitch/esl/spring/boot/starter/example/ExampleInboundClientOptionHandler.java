package com.atomscat.freeswitch.esl.spring.boot.starter.example;

import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.inbound.option.ServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.AbstractInboundClientOptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>ExampleInboundClientOptionHandler class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
@Slf4j
@Component
public class ExampleInboundClientOptionHandler extends AbstractInboundClientOptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(InboundClientOption inboundClientOption) {
        List<ServerOption> serverOptions = inboundClientOption.serverOptions();
        log.info("serverOptions before : {}", serverOptions);
        log.info("serverOptions after  : {}", serverOptions);
    }
}
