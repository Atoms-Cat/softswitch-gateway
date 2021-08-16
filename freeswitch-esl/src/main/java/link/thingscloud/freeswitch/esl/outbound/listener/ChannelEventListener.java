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

package link.thingscloud.freeswitch.esl.outbound.listener;

import io.netty.channel.ChannelHandlerContext;
import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>ChannelEventListener interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface ChannelEventListener {

    /**
     * <p>handleAuthRequest.</p>
     *
     * @param ctx a {@link ChannelHandlerContext} object.
     */
    void handleAuthRequest(ChannelHandlerContext ctx);

    /**
     * @param context  a {@link Context} object.
     * @param eslEvent a {@link EslEvent} object.
     */
    void onConnect(Context context, EslEvent eslEvent);

    /**
     * <p>handleEslEvent.</p>
     *
     * @param context  a {@link String} object.
     * @param eslEvent a {@link EslEvent} object.
     */
    void handleEslEvent(Context context, EslEvent eslEvent);

    /**
     * <p>handleDisconnectNotice.</p>
     *
     * @param remoteAddr a {@link String} object.
     */
    void handleDisconnectNotice(String remoteAddr, ChannelHandlerContext ctx);

}
