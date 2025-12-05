package com.github.seaxlab.core.component.allocate.impl;

import com.github.seaxlab.core.component.allocate.AllocateStrategy;
import com.github.seaxlab.core.component.allocate.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * An allocate strategy proxy for based on machine room nearside priority. An actual allocate strategy can be
 * specified.
 * <p>
 * If any consumer is alive in a machine room, the message queue of the broker which is deployed in the same machine
 * should only be allocated to those. Otherwise, those message queues can be shared along all consumers since there are
 * no alive consumer to monopolize them.
 */
@Slf4j
public class AllocateMachineRoomNearby implements AllocateStrategy {

  private final AllocateStrategy allocateStrategy;//actual allocate strategy
  private final MachineRoomResolver machineRoomResolver;

  public AllocateMachineRoomNearby(AllocateStrategy allocateStrategy, MachineRoomResolver machineRoomResolver)
    throws NullPointerException {
    if (allocateStrategy == null) {
      throw new NullPointerException("allocateStrategy is null");
    }

    if (machineRoomResolver == null) {
      throw new NullPointerException("machineRoomResolver is null");
    }

    this.allocateStrategy = allocateStrategy;
    this.machineRoomResolver = machineRoomResolver;
  }

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
      log.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}", consumerGroup, currentCID, cidAll);
      return result;
    }

    //group node by machine room
    Map<String/*machine room */, List<Node>> mr2Mq = new TreeMap<>();
    for (Node node : nodeAll) {
      String brokerMachineRoom = machineRoomResolver.brokerDeployIn(node);
      if (StringUtils.isNoneEmpty(brokerMachineRoom)) {
        if (mr2Mq.get(brokerMachineRoom) == null) {
          mr2Mq.put(brokerMachineRoom, new ArrayList<>());
        }
        mr2Mq.get(brokerMachineRoom).add(node);
      } else {
        throw new IllegalArgumentException("Machine room is null for node " + node);
      }
    }

    //group consumer by machine room
    Map<String/*machine room */, List<String/*clientId*/>> mr2c = new TreeMap<String, List<String>>();
    for (String cid : cidAll) {
      String consumerMachineRoom = machineRoomResolver.consumerDeployIn(cid);
      if (StringUtils.isNoneEmpty(consumerMachineRoom)) {
        if (mr2c.get(consumerMachineRoom) == null) {
          mr2c.put(consumerMachineRoom, new ArrayList<String>());
        }
        mr2c.get(consumerMachineRoom).add(cid);
      } else {
        throw new IllegalArgumentException("Machine room is null for consumer id " + cid);
      }
    }

    List<Node> allocateResults = new ArrayList<>();

    //1.allocate the node that deploy in the same machine room with the current consumer
    String currentMachineRoom = machineRoomResolver.consumerDeployIn(currentCID);
    List<Node> mqInThisMachineRoom = mr2Mq.remove(currentMachineRoom);
    List<String> consumerInThisMachineRoom = mr2c.get(currentMachineRoom);
    if (mqInThisMachineRoom != null && !mqInThisMachineRoom.isEmpty()) {
      // 就近原则时实际上的分配算法allocateStrategy
      allocateResults.addAll(
        allocateStrategy.allocate(consumerGroup, currentCID, mqInThisMachineRoom, consumerInThisMachineRoom));
    }

    //2.allocate the rest node to each machine room if there are no consumer alive in that machine room
    // 有剩余队列时，实际上的分配算法，allocateStrategy
    for (String machineRoom : mr2Mq.keySet()) {
      if (!mr2c.containsKey(
        machineRoom)) { // no alive consumer in the corresponding machine room, so all consumers share these queues
        allocateResults.addAll(allocateStrategy.allocate(consumerGroup, currentCID, mr2Mq.get(machineRoom), cidAll));
      }
    }

    return allocateResults;
  }

  @Override
  public String getName() {
    return "MACHINE_ROOM_NEARBY" + "-" + allocateStrategy.getName();
  }

  /**
   * A resolver object to determine which machine room do the message queues or clients are deployed in.
   * <p>
   * AllocateMachineRoomNearby will use the results to group the message queues and clients by machine room.
   * <p>
   * The result returned from the implemented method CANNOT be null.
   */
  public interface MachineRoomResolver {

    String brokerDeployIn(Node messageQueue);

    String consumerDeployIn(String clientID);
  }
}
