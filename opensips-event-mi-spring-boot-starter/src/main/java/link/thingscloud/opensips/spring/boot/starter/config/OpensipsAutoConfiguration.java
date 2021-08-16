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

package link.thingscloud.opensips.spring.boot.starter.config;

import link.thingscloud.opensips.event.EventClient;
import link.thingscloud.opensips.event.listener.ServerConnectionListener;
import link.thingscloud.opensips.event.listener.ServerEventListener;
import link.thingscloud.opensips.event.option.EventClientOption;
import link.thingscloud.opensips.spring.boot.starter.handler.EventClientOptionHandler;
import link.thingscloud.opensips.spring.boot.starter.propeties.OpensipsEventProperties;
import link.thingscloud.opensips.spring.boot.starter.template.DefaultEventClientOptionHandlerTemplate;
import link.thingscloud.opensips.spring.boot.starter.template.ServerConnectionListenerTemplate;
import link.thingscloud.opensips.spring.boot.starter.template.ServerEventListenerTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>FreeswitchEslAutoConfiguration class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({OpensipsEventProperties.class})
@ConditionalOnClass({EventClient.class})
public class OpensipsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServerEventListener.class)
    public ServerEventListener serverEventListener() {
        return new ServerEventListenerTemplate();
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

    @Bean
    @ConditionalOnMissingBean(EventClientOptionHandler.class)
    public EventClientOptionHandler eventClientOptionHandler() {
        return new DefaultEventClientOptionHandlerTemplate();
    }

    /**
     * <p>inboundClient.</p>
     *
     * @param eventClientOptionHandler a {@link EventClientOptionHandler} object.
     * @return a {@link EventClient} object.
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean(EventClient.class)
    public EventClient eventClient(@Autowired EventClientOptionHandler eventClientOptionHandler, @Autowired ServerEventListener outboundEventListener) {
        EventClientOption option = eventClientOptionHandler.getOption();
        option.addListener(outboundEventListener);
        log.info("outboundClient option : [{}]", option);
        return EventClient.newInstance(option);
    }

}
