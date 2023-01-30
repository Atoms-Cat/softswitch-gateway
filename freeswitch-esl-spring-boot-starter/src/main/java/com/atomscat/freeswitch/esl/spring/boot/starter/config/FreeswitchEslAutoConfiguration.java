/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atomscat.freeswitch.esl.spring.boot.starter.config;

import com.atomscat.freeswitch.esl.*;
import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.*;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.AmqpClientProperties;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.InboundClientProperties;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundServerProperties;
import com.atomscat.freeswitch.esl.spring.boot.starter.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * <p>FreeswitchEslAutoConfiguration class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({InboundClientProperties.class, OutboundServerProperties.class})
@ConditionalOnClass({InboundClient.class, OutboundServer.class})
@ComponentScan({"com.atomscat.freeswitch.esl.spring.boot.starter.propeties"})
public class FreeswitchEslAutoConfiguration {

    /**
     * <p>inboundClientPropertiesHandler.</p>
     *
     * @return a {@link InboundClientOptionHandler} object.
     */
    @Bean
    @ConditionalOnMissingBean(InboundClientOptionHandler.class)
    public InboundClientOptionHandler inboundClientOptionHandler(InboundClientProperties inboundClientProperties) {
        return new DefaultInboundClientOptionHandlerTemplate(inboundClientProperties);
    }

    /**
     * <p>inboundClientPropertiesHandler.</p>
     *
     * @return a {@link InboundClientOptionHandler} object.
     */
    @Bean
    @ConditionalOnMissingBean(OutboundServerOptionHandler.class)
    public OutboundServerOptionHandler outboundServerOptionHandler(OutboundServerProperties properties) {
        return new DefaultOutboundServerOptionHandlerTemplate(properties);
    }

    /**
     * <p>listener.</p>
     *
     * @return a {@link InboundEventListener} object.
     */
    @Bean
    @ConditionalOnMissingBean(InboundEventListener.class)
    public InboundEventListener listener(List<InboundEventHandler> eslEventHandlers,  InboundClient inboundClient) {
        return new InboundEventListenerTemplate(eslEventHandlers, inboundClient);
    }

    @Bean
    @ConditionalOnMissingBean(OutboundEventListener.class)
    public OutboundEventListener outboundEventListener( List<OutBoundConnectHandler> outBoundConnectHandlers,
                                                        List<OutBoundEventHandler> outBoundEventHandlers) {
        return new OutboundEventListenerTemplate(outBoundConnectHandlers, outBoundEventHandlers);
    }

    @Bean
    public MqEventHandler defaultMqEventHandler() {
        return new DefaultMqEventHandler();
    }

    @Bean
    public MqLoggingHandler defaultMqLoggingHandler() {
        return new DefaultMqLoggingHandler();
    }

    @Bean
    public MqListenerTemplate mqListenerTemplate( List<MqEventHandler> eventHandlers,  List<MqLoggingHandler> loggingHandlers,
                                                  AmqpClientProperties amqpClientProperties,  AmqpTemplate amqpTemplat) {
        return new MqListenerTemplate(eventHandlers, loggingHandlers, amqpClientProperties, amqpTemplat);
    }

    /**
     * <p>serverConnectionListener.</p>
     *
     * @return a {@link ServerConnectionListener} object.
     */
    @Bean
    @ConditionalOnMissingBean(ServerConnectionListener.class)
    public ServerConnectionListener serverConnectionListener() {
        return new ServerConnectionListenerTemplate();
    }

    /**
     * <p>inboundClient.</p>
     *
     * @param serverConnectionListener   a {@link ServerConnectionListener} object.
     * @param inboundClientOptionHandler a {@link InboundClientOptionHandler} object.
     * @return a {@link InboundClient} object.
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean(InboundClient.class)
    public InboundClient inboundClient( ServerConnectionListener serverConnectionListener,  InboundClientOptionHandler inboundClientOptionHandler) {
        InboundClientOption option = inboundClientOptionHandler.getOption();
        option.serverConnectionListener(serverConnectionListener);
        log.info("inboundClient properties : [{}]", option);
        log.info("inboundClient option : [{}]", option);
        return InboundClient.newInstance(option);
    }

    /**
     * <p>inboundClient.</p>
     *
     * @param outboundServerOptionHandler a {@link InboundClientOptionHandler} object.
     * @return a {@link OutboundServer} object.
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean(OutboundServer.class)
    public OutboundServer outboundServer( OutboundServerOptionHandler outboundServerOptionHandler,  OutboundEventListener outboundEventListener) {
        OutboundServerOption option = outboundServerOptionHandler.getOption();
        option.addListener(outboundEventListener);
        log.info("outboundServer option : [{}]", option);
        return OutboundServer.newInstance(option);
    }

}
