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

package com.atomscat.freeswitch.esl.example;

import com.atomscat.freeswitch.esl.IEslEventListener;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.inbound.option.InboundClientOption;
import com.atomscat.freeswitch.esl.inbound.option.ServerOption;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>EslInboundClientExample class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version 1.0.0
 */
public class EslInboundClientExample {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        InboundClientOption option = new InboundClientOption();

        option.defaultPassword("NewVois001")
                .addServerOption(new ServerOption("172.16.2.253", 8014));
        option.addEvents("all");

        option.addListener(new IEslEventListener() {
            @Override
            public void eventReceived(String address, EslEvent event) {
                System.out.println(address);
                System.out.println(event);
            }

            @Override
            public void backgroundJobResultReceived(String address, EslEvent event) {
                System.out.println(address);
                System.out.println(event);
            }
        });

        InboundClient inboundClient = InboundClient.newInstance(option);

        inboundClient.start();


        System.out.println(option.serverAddrOption().first());
        System.out.println(option.serverAddrOption().last());
        System.out.println(option.serverAddrOption().random());


    }

}
