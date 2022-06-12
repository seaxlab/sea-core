package com.github.seaxlab.core.component.allocate.impl;

import com.github.seaxlab.core.component.allocate.AllocateStrategy;
import com.github.seaxlab.core.component.allocate.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Computer room Hashing queue algorithm, such as Alipay logic room
 */
public class AllocateByMachineRoom implements AllocateStrategy {
    private Set<String> consumerIdcs;

    @Override
    public List<Node> allocate(String consumerGroup, String currentCID,
                               List<Node> nodeAll, List<String> cidAll) {
        List<Node> result = new ArrayList<>();
        int currentIndex = cidAll.indexOf(currentCID);
        if (currentIndex < 0) {
            return result;
        }
        List<Node> premqAll = new ArrayList<>();
        for (Node node : nodeAll) {
            String[] temp = node.getBrokerName().split("@");
            if (temp.length == 2 && consumerIdcs.contains(temp[0])) {
                premqAll.add(node);
            }
        }

        int mod = premqAll.size() / cidAll.size();
        int rem = premqAll.size() % cidAll.size();
        int startIndex = mod * currentIndex;
        int endIndex = startIndex + mod;
        for (int i = startIndex; i < endIndex; i++) {
            result.add(premqAll.get(i));
        }
        if (rem > currentIndex) {
            result.add(premqAll.get(currentIndex + mod * cidAll.size()));
        }
        return result;
    }

    @Override
    public String getName() {
        return "MACHINE_ROOM";
    }

    public Set<String> getConsumerIdcs() {
        return consumerIdcs;
    }

    public void setConsumerIdcs(Set<String> consumerIdcs) {
        this.consumerIdcs = consumerIdcs;
    }
}
