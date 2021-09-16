package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.util.EslEventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@EslEventName(EventNames.DTMF)
@Component
public class InboundDTMFHandler implements EslEventHandler  {

    @Autowired
    private InboundClient inboundClient;

    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        SendMsg sendMsg = new SendMsg(EslEventUtil.getCallerUniqueId(event));

        log.info("Inbound DTMF:[{}] [{}]  [{}]", address, coreUUID, JSON.toJSONString(event));


    }
}
