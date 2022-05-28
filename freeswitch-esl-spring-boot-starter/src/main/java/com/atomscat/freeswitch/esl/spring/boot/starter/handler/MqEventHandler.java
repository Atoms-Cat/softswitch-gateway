package com.atomscat.freeswitch.esl.spring.boot.starter.handler;

import com.rabbitmq.client.Channel;

/**
 *  mod_amqp TAP.Events
 * @author th158
 */
public interface MqEventHandler {

    void handle(Channel channel, String payload, Long tag, String contentType) throws Exception;
}
