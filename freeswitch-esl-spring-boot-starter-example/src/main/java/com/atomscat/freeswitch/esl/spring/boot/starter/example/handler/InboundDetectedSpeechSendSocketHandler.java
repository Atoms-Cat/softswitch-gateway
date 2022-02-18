package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 语音识别事件监听
 * <action application="set" data="fire_asr_events=true"/>
 * @author howell
 * @date 18/2/2022 10:48 AM
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
