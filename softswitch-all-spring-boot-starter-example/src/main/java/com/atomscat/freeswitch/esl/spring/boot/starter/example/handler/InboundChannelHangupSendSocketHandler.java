package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.InboundEventHandler;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.spring.boot.common.aop.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author th158
 */
@Slf4j
@EslEventName(EventNames.CHANNEL_HANGUP_COMPLETE)
@Component
public class InboundChannelHangupSendSocketHandler implements InboundEventHandler {


    @Override
    @RedisLock(lockName = EventNames.CHANNEL_HANGUP_COMPLETE, key = "coreUUID")
    public void handle(String address, EslEvent event, String coreUUID) {
        log.info("CHANNEL_HANGUP:  [{}]", JSON.toJSONString(event));

    }
}
