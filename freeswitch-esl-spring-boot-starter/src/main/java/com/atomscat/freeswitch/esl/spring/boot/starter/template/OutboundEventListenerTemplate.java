package com.atomscat.freeswitch.esl.spring.boot.starter.template;

import com.atomscat.freeswitch.esl.OutboundEventListener;
import com.atomscat.freeswitch.esl.outbound.handler.Context;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.DefaultOutBoundConnectHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.DefaultOutBoundEventHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.OutBoundConnectHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.OutBoundEventHandler;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.util.ArrayUtils;
import com.atomscat.freeswitch.esl.util.StringUtils;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author th158
 */
@Slf4j
@RequiredArgsConstructor
public class OutboundEventListenerTemplate implements OutboundEventListener, InitializingBean {

    private final List<OutBoundConnectHandler> outBoundConnectHandlers;
    private final List<OutBoundEventHandler> outBoundEventHandlers;

    private final Map<String, List<OutBoundEventHandler>> handlerTable = Maps.newHashMap();
    private final OutBoundConnectHandler defaultOutBoundConnectHandler = new DefaultOutBoundConnectHandler();
    private final OutBoundEventHandler defaultOutBoundEslEventHandler = new DefaultOutBoundEventHandler();

    @Override
    public void handleEslEvent(Context context, EslEvent event) {
        String eventName = event.getEventName();
        log.info("fs-esl outbound event name: {}", eventName);
        List<OutBoundEventHandler> handlers = handlerTable.get(eventName);
        if (!CollectionUtils.isEmpty(handlers)) {
            handlers.forEach(eventHandler -> eventHandler.handler(context, event));
            return;
        }
        defaultOutBoundEslEventHandler.handler(context, event);
    }

    @Override
    public void onConnect(Context context, EslEvent eslEvent) {
        if (!CollectionUtils.isEmpty(outBoundConnectHandlers)) {
            outBoundConnectHandlers.forEach(handler -> handler.onConnect(context, eslEvent));
            return;
        }
        defaultOutBoundConnectHandler.onConnect(context, eslEvent);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("OutBoundEslEventListener init ...");
        for (OutBoundEventHandler outBoundEslEventHandler : outBoundEventHandlers) {
            Class<? extends OutBoundEventHandler> eventHandleImpl = outBoundEslEventHandler.getClass();
            EslEventName eventName = eventHandleImpl.getAnnotation(EslEventName.class);
            if (eventName == null) {
                continue;
            }
            String[] values = eventName.value();
            if (!ArrayUtils.isEmpty(values)) {
                for (String value : values) {
                    if (StringUtils.isNotBlank(value)) {
                        log.info("IOutBoundEslEventListener add EventName[{}], OutBoundEslEventHandler[{}] to tables ...", value, outBoundEslEventHandler.getClass());
                        handlerTable.computeIfAbsent(value, k -> new ArrayList<>(4)).add(outBoundEslEventHandler);
                    }
                }
            }
        }
    }

}
