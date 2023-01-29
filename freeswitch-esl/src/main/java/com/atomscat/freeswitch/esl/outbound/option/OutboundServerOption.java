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

package com.atomscat.freeswitch.esl.outbound.option;

import com.atomscat.freeswitch.esl.OutboundEventListener;
import com.atomscat.freeswitch.esl.ServerConnectionListener;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>OutboundServerOption class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version 1.0.0
 */
@ToString
public class OutboundServerOption {

    private final List<OutboundEventListener> listeners = new ArrayList<>();
    private final List<String> events = new ArrayList<>();
    private ServerOption serverOption = null;
    private int sndBufSize = 65535;
    private int rcvBufSize = 65535;
    private int parentGroupThread = Runtime.getRuntime().availableProcessors() * 2;
    private int workerGroupThread = Runtime.getRuntime().availableProcessors() * 2;
    private int publicExecutorThread = Runtime.getRuntime().availableProcessors() * 2;
    private int callbackExecutorThread = Runtime.getRuntime().availableProcessors() * 2;
    private int defaultTimeoutSeconds = 5;
    private String defaultPassword = "ClueCon";
    private int readTimeoutSeconds = 120;
    private int readerIdleTimeSeconds = 120;
    private boolean disablePublicExecutor = false;
    private boolean performance = false;
    private long performanceCostTime = 200;
    private boolean eventPerformance = false;
    private long eventPerformanceCostTime = 200;
    private ServerConnectionListener serverConnectionListener = null;

    /**
     * <p>sndBufSize.</p>
     *
     * @return a int.
     */
    public int sndBufSize() {
        return sndBufSize;
    }

    /**
     * <p>sndBufSize.</p>
     *
     * @param sndBufSize a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption sndBufSize(int sndBufSize) {
        this.sndBufSize = sndBufSize;
        return this;
    }

    /**
     * <p>rcvBufSize.</p>
     *
     * @return a int.
     */
    public int rcvBufSize() {
        return rcvBufSize;
    }

    /**
     * <p>rcvBufSize.</p>
     *
     * @param rcvBufSize a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption rcvBufSize(int rcvBufSize) {
        this.rcvBufSize = rcvBufSize;
        return this;
    }

    /**
     * <p>workerGroupThread.</p>
     *
     * @return a int.
     */
    public int workerGroupThread() {
        return workerGroupThread;
    }

    /**
     * <p>workerGroupThread.</p>
     *
     * @param workerGroupThread a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption workerGroupThread(int workerGroupThread) {
        this.workerGroupThread = workerGroupThread;
        return this;
    }

    public int parentGroupThread() {
        return parentGroupThread;
    }

    public void setParentGroupThread(int parentGroupThread) {
        this.parentGroupThread = parentGroupThread;
    }

    /**
     * <p>publicExecutorThread.</p>
     *
     * @return a int.
     */
    public int publicExecutorThread() {
        return publicExecutorThread;
    }

    /**
     * <p>publicExecutorThread.</p>
     *
     * @param publicExecutorThread a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption publicExecutorThread(int publicExecutorThread) {
        this.publicExecutorThread = publicExecutorThread;
        return this;
    }

    /**
     * <p>callbackExecutorThread.</p>
     *
     * @return a int.
     */
    public int callbackExecutorThread() {
        return callbackExecutorThread;
    }

    /**
     * <p>callbackExecutorThread.</p>
     *
     * @param callbackExecutorThread a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption callbackExecutorThread(int callbackExecutorThread) {
        this.callbackExecutorThread = callbackExecutorThread;
        return this;
    }

    /**
     * <p>defaultTimeoutSeconds.</p>
     *
     * @return a int.
     */
    public int defaultTimeoutSeconds() {
        return defaultTimeoutSeconds;
    }

    /**
     * <p>defaultTimeoutSeconds.</p>
     *
     * @param defaultTimeoutSeconds a int.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption defaultTimeoutSeconds(int defaultTimeoutSeconds) {
        this.defaultTimeoutSeconds = defaultTimeoutSeconds;
        return this;
    }

    /**
     * <p>defaultPassword.</p>
     *
     * @return a {@link String} object.
     */
    public String defaultPassword() {
        return defaultPassword;
    }

    /**
     * <p>defaultPassword.</p>
     *
     * @param defaultPassword a {@link String} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption defaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
        return this;
    }


    /**
     * <p>readTimeoutSeconds.</p>
     *
     * @return a {@link Integer} object.
     */
    public int readTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    /**
     * <p>readTimeoutSeconds.</p>
     *
     * @param readTimeoutSeconds a {@link Integer} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption readTimeoutSeconds(int readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
        return this;
    }

    /**
     * <p>readerIdleTimeSeconds.</p>
     *
     * @return a {@link Integer} object.
     */
    public int readerIdleTimeSeconds() {
        return readerIdleTimeSeconds;
    }

    /**
     * <p>readerIdleTimeSeconds.</p>
     * <p>
     * 读空闲时长
     *
     * @param readerIdleTimeSeconds a {@link Integer} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption readerIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        return this;
    }


    /**
     * <p>
     * disable public executor thread pool then message thread safety.
     * 处理事件消息时不使用独立线程池，可以使得消息线程安全
     * </p>
     *
     * @return a boolean.
     */
    public boolean disablePublicExecutor() {
        return disablePublicExecutor;
    }

    /**
     * <p>
     * disable public executor thread pool then message thread safety.
     * 处理事件消息时不使用独立线程池，可以使得消息线程安全
     * </p>
     *
     * @param disablePublicExecutor a boolean.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption disablePublicExecutor(boolean disablePublicExecutor) {
        this.disablePublicExecutor = disablePublicExecutor;
        return this;
    }

    /**
     * <p>performance.</p>
     *
     * @return a boolean.
     */
    public boolean performance() {
        return performance;
    }

    /**
     * <p>performance.</p>
     *
     * @param performance a boolean.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption performance(boolean performance) {
        this.performance = performance;
        return this;
    }

    /**
     * <p>performanceCostTime.</p>
     *
     * @return a long.
     */
    public long performanceCostTime() {
        return performanceCostTime;
    }

    /**
     * <p>performanceCostTime.</p>
     *
     * @param performanceCostTime a long.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption performanceCostTime(long performanceCostTime) {
        this.performanceCostTime = performanceCostTime;
        return this;
    }

    /**
     * <p>eventPerformance.</p>
     *
     * @return a boolean.
     */
    public boolean eventPerformance() {
        return eventPerformance;
    }

    /**
     * <p>eventPerformance.</p>
     *
     * @param eventPerformance a boolean.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption eventPerformance(boolean eventPerformance) {
        this.eventPerformance = eventPerformance;
        return this;
    }

    /**
     * <p>eventPerformanceCostTime.</p>
     *
     * @return a long.
     */
    public long eventPerformanceCostTime() {
        return eventPerformanceCostTime;
    }

    /**
     * <p>eventPerformanceCostTime.</p>
     *
     * @param eventPerformanceCostTime a long.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption eventPerformanceCostTime(long eventPerformanceCostTime) {
        this.eventPerformanceCostTime = eventPerformanceCostTime;
        return this;
    }

    /**
     * <p>serverConnectionListener.</p>
     *
     * @param serverConnectionListener a {@link ServerConnectionListener} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption serverConnectionListener(ServerConnectionListener serverConnectionListener) {
        this.serverConnectionListener = serverConnectionListener;
        return this;
    }


    /**
     * <p>serverOptions.</p>
     *
     * @return a {@link List} object.
     */
    public ServerOption serverOption() {
        return serverOption;
    }

    /**
     * @param serverOption a {@link ServerOption} object.
     */
    public void addServerOption(ServerOption serverOption) {
        this.serverOption = serverOption;
    }

    /**
     * <p>addListener.</p>
     *
     * @param listener a {@link OutboundEventListener} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption addListener(OutboundEventListener listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * <p>removeListener.</p>
     *
     * @param listener a {@link OutboundEventListener} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption removeListener(OutboundEventListener listener) {
        listeners.remove(listener);
        return this;
    }

    /**
     * <p>listeners.</p>
     *
     * @return a {@link List} object.
     */
    public List<OutboundEventListener> listeners() {
        return listeners;
    }


    /**
     * <p>events.</p>
     *
     * @return a {@link List} object.
     */
    public List<String> events() {
        return events;
    }

    /**
     * <p>addEvents.</p>
     *
     * @param addEvents a {@link String} object.
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption addEvents(String... addEvents) {
        if (addEvents == null) {
            return this;
        }

        List<String> list = new ArrayList<>();
        for (String addEvent : addEvents) {
            if (!events().contains(addEvent)) {
                list.add(addEvent);
            }
        }
        if (!list.isEmpty()) {
            events.addAll(list);
        }
        return this;
    }

    /**
     * <p>cancelEvents.</p>
     *
     * @return a {@link OutboundServerOption} object.
     */
    public OutboundServerOption cancelEvents() {
        if (!events.isEmpty()) {
            events.clear();
        }
        return this;
    }


}
