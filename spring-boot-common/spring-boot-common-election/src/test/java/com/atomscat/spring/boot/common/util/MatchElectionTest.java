package com.atomscat.spring.boot.common.util;

import com.alibaba.fastjson.JSON;
import com.atomscat.spring.boot.common.param.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MatchElectionTest {

    @Test
    public void offsetMatchElection() {
        List<BaseElectionProducerParam> producerParams = new ArrayList<>(16);
        List<BaseElectionConsumerParam> consumerParams = new ArrayList<>(16);

        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-1"));
        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-2"));
        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-3"));
        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-4"));
        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-5"));
        producerParams.add(new FreeswitchProducerParam().setNodeId("FS-node-6"));

        consumerParams.add(new SoftswitchGatewayConsumerParam().setNodeId("SW-node-1"));
        consumerParams.add(new SoftswitchGatewayConsumerParam().setNodeId("SW-node-2"));
        consumerParams.add(new SoftswitchGatewayConsumerParam().setNodeId("SW-node-3"));
        consumerParams.add(new SoftswitchGatewayConsumerParam().setNodeId("SW-node-4"));
        MatchElection matchElection = new MatchElection();
        List<ElectionResultParam> list = matchElection.offsetMatchElection(2, producerParams, consumerParams);
        for (ElectionResultParam resultParam : list) {
            System.out.println(JSON.toJSONString(resultParam));
        }

    }
}
