package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/*
 dialplan xml setting : <action application="set" data="fire_asr_events=true"/>
 */
/**
 * 语音识别事件监听
 * @author howell
 */
@Slf4j
@EslEventName(EventNames.DETECTED_SPEECH)
@Component
public class InboundDetectedSpeechSendSocketHandler implements EslEventHandler {

    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        log.info("address[{}] EslEvent[{}]", address, EslHelper.formatEslEvent(event));
    }
}
