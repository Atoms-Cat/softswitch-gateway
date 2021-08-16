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

package link.thingscloud.freeswitch.esl;

import link.thingscloud.freeswitch.esl.exception.InboundClientException;
import link.thingscloud.freeswitch.esl.outbound.NettyOutboundClient;
import link.thingscloud.freeswitch.esl.outbound.option.OutboundClientOption;

/**
 * 保证单例对象
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
class OutboundClientFactory {

    private OutboundClient outboundClient = null;

    private OutboundClientFactory() {
    }

    static OutboundClientFactory getInstance() {
        return InboundClientFactoryInstance.INSTANCE;
    }

    synchronized OutboundClient newOutboundClient(OutboundClientOption option) {
        if (outboundClient == null) {
            outboundClient = new NettyOutboundClient(option == null ? new OutboundClientOption() : option);
            return outboundClient;
        }
        throw new InboundClientException("outboundClient has been created already, instance : [" + outboundClient + "]!");
    }

    OutboundClient newOutboundClient() {
        if (outboundClient == null) {
            throw new InboundClientException("outboundClient is null, you must be create it first.");
        }
        return outboundClient;
    }

    private static class InboundClientFactoryInstance {
        private static final OutboundClientFactory INSTANCE = new OutboundClientFactory();
    }

}
