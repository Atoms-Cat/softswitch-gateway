package com.atomscat.freeswitch.esl.spring.boot.starter.propeties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "com.atomscat.freeswitch.amqp")
public class AmqpClientProperties {

    private String commandsQueues;

    private String eventsQueues;

    private String loggingQueues;

    public String getCommandsQueues() {
        return commandsQueues;
    }

    public String getEventsQueues() {
        return eventsQueues;
    }

    public String getLoggingQueues() {
        return loggingQueues;
    }
}
