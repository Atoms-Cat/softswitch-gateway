package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.rabbitmq.client.Channel;

/**
 * mod_amqp TAP.Logging
 * @author th158
 */
public interface MqLoggingHandler {

    void handle(Channel channel, String payload, Long tag, String contentType) throws Exception;
}
