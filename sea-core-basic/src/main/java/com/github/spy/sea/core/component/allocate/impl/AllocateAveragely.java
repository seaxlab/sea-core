package com.github.spy.sea.core.component.allocate.impl;

import com.github.spy.sea.core.component.allocate.AllocateStrategy;
import com.github.spy.sea.core.component.allocate.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Average Hashing queue algorithm
 */
@Slf4j
public class AllocateAveragely implements AllocateStrategy {

    @Override
    public List<Node> allocate(String consumerGroup, String currentCID, List<Node> nodeAll, List<String> cidAll) {
        if (currentCID == null || currentCID.length() < 1) {
            throw new IllegalArgumentException("currentCID is empty");
        }
        if (nodeAll == null || nodeAll.isEmpty()) {
            throw new IllegalArgumentException("nodeAll is null or nodeAll empty");
        }
        if (cidAll == null || cidAll.isEmpty()) {
            throw new IllegalArgumentException("cidAll is null or cidAll empty");
        }

        List<Node> result = new ArrayList<>();
        if (!cidAll.contains(currentCID)) {
            log.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}",
                    consumerGroup,
                    currentCID,
                    cidAll);
            return result;
        }

        int index = cidAll.indexOf(currentCID);
        int mod = nodeAll.size() % cidAll.size();
        int averageSize =
                nodeAll.size() <= cidAll.size() ? 1 : (mod > 0 && index < mod ? nodeAll.size() / cidAll.size()
                        + 1 : nodeAll.size() / cidAll.size());
        int startIndex = (mod > 0 && index < mod) ? index * averageSize : index * averageSize + mod;
        int range = Math.min(averageSize, nodeAll.size() - startIndex);
        for (int i = 0; i < range; i++) {
            result.add(nodeAll.get((startIndex + i) % nodeAll.size()));
        }
        return result;
    }

    @Override
    public String getName() {
        return "AVG";
    }
}
