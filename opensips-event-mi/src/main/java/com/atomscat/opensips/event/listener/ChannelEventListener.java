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

package com.atomscat.opensips.event.listener;

import com.atomscat.opensips.event.handler.Context;
import io.netty.channel.ChannelHandlerContext;


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
     * @param context a {@link Context} object.
     * @param msg     a {@link Object} object.
     */
    void onConnect(Context context, Object msg);

    /**
     * <p>handleEslEvent.</p>
     *
     * @param context a {@link String} object.
     * @param msg     a {@link Object} object.
     */
    void handleEvent(Context context, Object msg);

    /**
     *
     * @param remoteAddr a {@link String} object.
     * @param ctx a {@link ChannelHandlerContext} object.
     */
    void handleDisconnectNotice(String remoteAddr, ChannelHandlerContext ctx);
}
