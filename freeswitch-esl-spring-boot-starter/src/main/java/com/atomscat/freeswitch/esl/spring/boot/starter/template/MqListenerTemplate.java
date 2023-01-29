package com.atomscat.freeswitch.esl.spring.boot.starter.template;

import com.atomscat.freeswitch.esl.spring.boot.starter.handler.MqCommandsClient;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.MqEventHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.MqLoggingHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.AmqpClientProperties;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

/**
 * @author th158
 */
@Slf4j
@RequiredArgsConstructor
public class MqListenerTemplate implements InitializingBean, MqCommandsClient {

    private final List<MqEventHandler> eventHandlers;

    private final List<MqLoggingHandler> loggingHandlers;

    private final AmqpClientProperties amqpClientProperties;

    private final AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "#{amqpClientProperties.getEventsQueues()}")
    public void eventsListen(Channel channel, String payload, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.CONTENT_TYPE) String contentType) {
        try {
            log.debug("fs.events : {}", payload);
            for (MqEventHandler amqpEventHandler : eventHandlers) {
                amqpEventHandler.handle(channel, payload, tag, contentType);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("fs.events ", e);
        }
    }

    @RabbitListener(queues = "#{amqpClientProperties.getLoggingQueues()}")
    public void loggingListen(Channel channel, String payload, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.CONTENT_TYPE) String contentType) {
        try {
            log.debug("fs.logging : {}", payload);
            for (MqLoggingHandler amqpLoggingHandler : loggingHandlers) {
                amqpLoggingHandler.handle(channel, payload, tag, contentType);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("fs.logging ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("MqListener init ...");
    }

    @Override
    public void sendCommands(String routingKey, String command, String arg) throws Exception {
        amqpTemplate.convertAndSend(amqpClientProperties.getCommandsQueues(), routingKey, command + " " + arg);
        log.info("sendCommands {} {} {}", routingKey, command, arg);
    }
}
