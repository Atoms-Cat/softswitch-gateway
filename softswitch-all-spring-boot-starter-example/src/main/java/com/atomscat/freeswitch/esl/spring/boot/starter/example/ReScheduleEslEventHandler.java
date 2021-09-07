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

package com.atomscat.freeswitch.esl.spring.boot.starter.example;

import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.AbstractEslEventHandler;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.transport.message.EslMessage;
import com.atomscat.spring.boot.common.aop.annotation.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>ReScheduleEslEventHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Slf4j
@EslEventName(EventNames.RE_SCHEDULE)
@Component
public class ReScheduleEslEventHandler extends AbstractEslEventHandler {

    @Autowired
    private InboundClient inboundClient;

    /**
     * {@inheritDoc}
     */
    @Logging
    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        log.debug("ReScheduleEslEventHandler handle address [{}] EslEvent[{}].", address, event);
        log.debug("{}", inboundClient);
        EslMessage eslMessage = inboundClient.sendSyncApiCommand(address, "version", null);
        log.debug("{}", EslHelper.formatEslMessage(eslMessage));
    }
}