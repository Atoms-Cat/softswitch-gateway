package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DefaultMqEventHandler extends AbstractMqEventHandler{
    @Override
    public void handle(Channel channel, String payload, Long tag, String contentType) throws Exception {
        log.info("fs.events : {}", payload);
    }
}
