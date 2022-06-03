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

package com.atomscat.freeswitch.esl.spring.boot.starter.propeties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>ServerProperties class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.atomscat.freeswitch.esl.outbound.server")
public class ServerProperties {
    private String host;
    /**
     * listen-port
     * @see /usr/local/freeswitch/conf/autoload_configs/event_socket.conf.xml*
     */
    private int port = 8021;
    private int timeoutSeconds;
    /**
     * password
     * @see /usr/local/freeswitch/conf/autoload_configs/event_socket.conf.xml
     */
    private String password;
    /**
     * mod_amqp module setting commands node binding_key param
     * @see /usr/local/freeswitch/conf/autoload_configs/amqp.conf.xml
     * ```
     * <commands>
     *     <profile name="default">
     *       <connections>
     *         <connection name="primary">
     *          ...
     *         </connection>
     *       </connections>
     *       <params>
     *         <param name="exchange-name" value="TAP.Commands"/>
     *         <param name="binding_key" value="commandBindingKey"/>
     *         ...
     *       </params>
     *     </profile>
     *   </commands>
     *   ```
     */
    private String routingKey;
}
