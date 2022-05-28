package com.atomscat.freeswitch.esl.spring.boot.starter.handler;



public interface MqCommandsClient {

    void sendCommands(String routingKey, String command, String arg) throws Exception;
}
