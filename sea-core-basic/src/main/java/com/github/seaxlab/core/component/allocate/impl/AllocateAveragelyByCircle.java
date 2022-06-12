package com.github.seaxlab.core.component.allocate.impl;

import com.github.seaxlab.core.component.allocate.AllocateStrategy;
import com.github.seaxlab.core.component.allocate.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Cycle average Hashing queue algorithm
 */
@Slf4j
public class AllocateAveragelyByCircle implements AllocateStrategy {

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

        List<Node> result = new ArrayList<Node>();
        if (!cidAll.contains(currentCID)) {
            log.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}",
                    consumerGroup,
                    currentCID,
                    cidAll);
            return result;
        }

        int index = cidAll.indexOf(currentCID);
        for (int i = index; i < nodeAll.size(); i++) {
            if (i % cidAll.size() == index) {
                result.add(nodeAll.get(i));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "AVG_BY_CIRCLE";
    }
}
