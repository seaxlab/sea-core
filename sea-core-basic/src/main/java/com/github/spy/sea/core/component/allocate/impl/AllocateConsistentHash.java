package com.github.spy.sea.core.component.allocate.impl;

import com.github.spy.sea.core.component.allocate.AllocateStrategy;
import com.github.spy.sea.core.component.allocate.Node;
import com.github.spy.sea.core.component.consistenthash.ConsistentHashRouter;
import com.github.spy.sea.core.component.consistenthash.HashFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Consistent Hashing queue algorithm
 */
@Slf4j
public class AllocateConsistentHash implements AllocateStrategy {

    private final int virtualNodeCnt;
    private final HashFunction customHashFunction;

    public AllocateConsistentHash() {
        this(10);
    }

    public AllocateConsistentHash(int virtualNodeCnt) {
        this(virtualNodeCnt, null);
    }

    public AllocateConsistentHash(int virtualNodeCnt, HashFunction customHashFunction) {
        if (virtualNodeCnt < 0) {
            throw new IllegalArgumentException("illegal virtualNodeCnt :" + virtualNodeCnt);
        }
        this.virtualNodeCnt = virtualNodeCnt;
        this.customHashFunction = customHashFunction;
    }

    @Override
    public List<Node> allocate(String consumerGroup, String currentCID, List<Node> nodeAll,
                               List<String> cidAll) {

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

        Collection<ClientNode> cidNodes = new ArrayList<ClientNode>();
        for (String cid : cidAll) {
            cidNodes.add(new ClientNode(cid));
        }

        final ConsistentHashRouter<ClientNode> router; //for building hash ring
        if (customHashFunction != null) {
            router = new ConsistentHashRouter<ClientNode>(cidNodes, virtualNodeCnt, customHashFunction);
        } else {
            router = new ConsistentHashRouter<ClientNode>(cidNodes, virtualNodeCnt);
        }

        List<Node> results = new ArrayList<Node>();
        for (Node node : nodeAll) {
            ClientNode clientNode = router.routeNode(node.toString());
            if (clientNode != null && currentCID.equals(clientNode.getKey())) {
                results.add(node);
            }
        }

        return results;

    }

    @Override
    public String getName() {
        return "CONSISTENT_HASH";
    }

    private static class ClientNode implements com.github.spy.sea.core.component.consistenthash.Node {
        private final String clientID;

        public ClientNode(String clientID) {
            this.clientID = clientID;
        }

        @Override
        public String getKey() {
            return clientID;
        }
    }

}
