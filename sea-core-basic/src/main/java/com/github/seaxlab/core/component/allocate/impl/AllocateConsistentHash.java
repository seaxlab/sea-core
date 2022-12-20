package com.github.seaxlab.core.component.allocate.impl;

import com.github.seaxlab.core.component.allocate.AllocateStrategy;
import com.github.seaxlab.core.component.consistenthash.ConsistentHashRouter;
import com.github.seaxlab.core.component.consistenthash.HashFunction;
import com.github.seaxlab.core.component.consistenthash.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

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
  public List<com.github.seaxlab.core.component.allocate.Node> allocate(String consumerGroup, String currentCID,
    List<com.github.seaxlab.core.component.allocate.Node> nodeAll, List<String> cidAll) {

    if (currentCID == null || currentCID.length() < 1) {
      throw new IllegalArgumentException("currentCID is empty");
    }
    if (nodeAll == null || nodeAll.isEmpty()) {
      throw new IllegalArgumentException("nodeAll is null or nodeAll empty");
    }
    if (cidAll == null || cidAll.isEmpty()) {
      throw new IllegalArgumentException("cidAll is null or cidAll empty");
    }

    List<com.github.seaxlab.core.component.allocate.Node> result = new ArrayList<com.github.seaxlab.core.component.allocate.Node>();
    if (!cidAll.contains(currentCID)) {
      log.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}", consumerGroup, currentCID, cidAll);
      return result;
    }

    Collection<ClientNode> cidNodes = new ArrayList<ClientNode>();
    for (String cid : cidAll) {
      cidNodes.add(new ClientNode(cid));
    }

    final ConsistentHashRouter<ClientNode> router; //for building hash ring
    if (customHashFunction != null) {
      router = new ConsistentHashRouter<>(cidNodes, virtualNodeCnt, customHashFunction);
    } else {
      router = new ConsistentHashRouter<>(cidNodes, virtualNodeCnt);
    }

    List<com.github.seaxlab.core.component.allocate.Node> results = new ArrayList<com.github.seaxlab.core.component.allocate.Node>();
    for (com.github.seaxlab.core.component.allocate.Node node : nodeAll) {
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

  private static class ClientNode implements Node {

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
