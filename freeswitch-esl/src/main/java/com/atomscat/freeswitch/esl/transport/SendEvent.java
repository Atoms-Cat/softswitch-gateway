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

package com.atomscat.freeswitch.esl.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>SendEvent class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version 1.0.0
 */
public class SendEvent implements Serializable {
    private static final String LINE_TERMINATOR = "\n";

    private final List<String> msgLines = new ArrayList<>();

    /**
     * Constructor for use with outbound socket client only.  This client mode does not need a call
     * UUID for context.
     *
     * @param name part of line
     */
    public SendEvent(String name) {
        msgLines.add("sendevent " + name);
    }

    /**
     * A generic method to add a message line. The constructed line in the sent message will be in the
     * form:
     * <pre>
     *   name: value
     * </pre>
     *
     * @param name  part of line
     * @param value part of line
     * @return a {@link SendEvent} object.
     */
    public SendEvent addLine(String name, String value) {
        msgLines.add(name + ": " + value);
        return this;
    }

    /**
     * A generic method to add a message line. The constructed line in the sent message will be in the
     * form:
     * <pre>
     *   name: value
     * </pre>
     *
     * @param line part of line
     */
    public SendEvent addBody(String line) {
        msgLines.add(LINE_TERMINATOR + line);
        return this;
    }

    /**
     * The list of strings that make up the message to send to FreeSWITCH.
     *
     * @return list of strings, as they were added to this message.
     */
    public List<String> getMsgLines() {
        return msgLines;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SendEvent: ");
        if (msgLines.size() > 1) {
            sb.append(msgLines.get(0));
        } else if (msgLines.size() > 0) {
            sb.append(0);
        }
        return sb.toString();
    }

}
