package com.atomscat.spring.boot.common.util;

import com.atomscat.spring.boot.common.param.BaseElectionConsumerParam;
import com.atomscat.spring.boot.common.param.BaseElectionProducerParam;
import com.atomscat.spring.boot.common.param.ElectionResultParam;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author howell
 */
public class MatchElection {

    /**
     * offset match election
     *
     * @param monitor        monitor quantity
     * @param producerParams producer server
     * @param consumerParams consumer client, offset and make up client node.
     */
    public List<ElectionResultParam> offsetMatchElection(Integer monitor, List<BaseElectionProducerParam> producerParams, List<BaseElectionConsumerParam> consumerParams) {
        // Calculate the minimum number of listening services required
        int consumerMiniSize = producerParams.size() + monitor - 1;
        // Complementary monitoring service
        if (consumerParams.size() < consumerMiniSize) {
            int diff = consumerMiniSize - consumerParams.size();
            for (int i = 0; i < diff; i++) {
                consumerParams.add(consumerParams.get(i));
            }
        }
        List<ElectionResultParam> resultParams = new Vector<>(16);

        // Offset, plus one for each producer server processed
        AtomicInteger offset = new AtomicInteger(0);
        producerParams.forEach(baseElectionProducerParam -> {
            List<BaseElectionConsumerParam> electionList = new Vector<>(16);
            // Offset allocation listening service
            for (int j = offset.get(); j < (monitor + offset.get()); j++) {
                electionList.add(consumerParams.get(j));
            }
            offset.getAndIncrement();
            resultParams.add(new ElectionResultParam().setKey(baseElectionProducerParam).setValueList(electionList));
        });
        return resultParams;
    }
}
