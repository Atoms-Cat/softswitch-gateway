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

import io.netty.bootstrap.ServerBootstrap;
import link.thingscloud.freeswitch.esl.outbound.option.OutboundClientOption;

/**
 * <p>InboundClient interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface OutboundClient extends OutboundClientService, OutboundClientCommand {

    /**
     * <p>newInstance.</p>
     *
     * @param option a {@link OutboundClientOption} object.
     * @return a {@link OutboundClient} object.
     */
    static OutboundClient newInstance(OutboundClientOption option) {
        return OutboundClientFactory.getInstance().newOutboundClient(option);
    }

    /**
     * 获取客户可配置选项
     *
     * @return this
     */
    OutboundClientOption option();

    /**
     * 获取 netty bootstrap
     *
     * @return
     */
    ServerBootstrap bootstrap();
}
