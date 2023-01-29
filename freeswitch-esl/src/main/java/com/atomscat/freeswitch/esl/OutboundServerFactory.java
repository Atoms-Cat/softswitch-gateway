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

package com.atomscat.freeswitch.esl;

import com.atomscat.freeswitch.esl.exception.InboundClientException;
import com.atomscat.freeswitch.esl.exception.OutboundServerException;
import com.atomscat.freeswitch.esl.outbound.NettyOutboundServer;
import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;

/**
 * 保证单例对象
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 */
class OutboundServerFactory {

    private OutboundServer outboundServer = null;

    private OutboundServerFactory() {
    }

    static OutboundServerFactory getInstance() {
        return OutboundServerFactoryInstance.INSTANCE;
    }

    synchronized OutboundServer newOutboundServer(OutboundServerOption option) {
        if (outboundServer == null) {
            outboundServer = new NettyOutboundServer(option == null ? new OutboundServerOption() : option);
            return outboundServer;
        }
        throw new OutboundServerException("outboundServer has been created already, instance : [" + outboundServer + "]!");
    }

    OutboundServer newOutboundServer() {
        if (outboundServer == null) {
            throw new OutboundServerException("outboundServer is null, you must be create it first.");
        }
        return outboundServer;
    }

    private static class OutboundServerFactoryInstance {
        private static final OutboundServerFactory INSTANCE = new OutboundServerFactory();
    }

}
