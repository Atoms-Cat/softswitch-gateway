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

package link.thingscloud.opensips.event;


import link.thingscloud.opensips.event.exception.EventClientException;
import link.thingscloud.opensips.event.option.EventClientOption;
import link.thingscloud.opensips.event.service.NettyEventService;

/**
 * 保证单例对象
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
class EventClientFactory {

    private EventClient eventClient = null;

    private EventClientFactory() {
    }

    static EventClientFactory getInstance() {
        return InboundClientFactoryInstance.INSTANCE;
    }

    synchronized EventClient newEventClient(EventClientOption option) {
        if (eventClient == null) {
            eventClient = new NettyEventService(option == null ? new EventClientOption() : option);
            return eventClient;
        }
        throw new EventClientException("opensips event has been created already, instance : [" + eventClient + "]!");
    }

    EventClient newEventClient() {
        if (eventClient == null) {
            throw new EventClientException("opensips event client is null, you must be create it first.");
        }
        return eventClient;
    }

    private static class InboundClientFactoryInstance {
        private static final EventClientFactory INSTANCE = new EventClientFactory();
    }

}
