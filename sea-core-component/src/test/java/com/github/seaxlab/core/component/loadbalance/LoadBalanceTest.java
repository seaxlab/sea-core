package com.github.seaxlab.core.component.loadbalance;

import com.github.seaxlab.core.component.BaseCoreTest;
import com.github.seaxlab.core.component.loadbalance.impl.ConsistentHashLoadBalance;
import com.github.seaxlab.core.component.loadbalance.impl.RandomLoadBalance;
import com.github.seaxlab.core.component.loadbalance.impl.RoundRobinLoadBalance;
import com.github.seaxlab.core.component.loadbalance.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public class LoadBalanceTest extends BaseCoreTest {

  private List<Node> nodes;

  @Before
  public void before() {
    nodes = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      Node node = new Node();
      node.setId("" + i);
      nodes.add(node);
    }
  }

  @Test
  public void RoundRobinTest() throws Exception {
    LoadBalance loadBalance = new RoundRobinLoadBalance(nodes);
    for (int i = 0; i < 100; i++) {
      Optional<Node> optional = loadBalance.select();
      log.info("key={},node={}", i, optional.get().getId());
    }
  }

  @Test
  public void weightBasedRoundRobinTest() throws Exception {
    nodes.get(1).setWeight(2);
    LoadBalance loadBalance = new RoundRobinLoadBalance(nodes);
    for (int i = 0; i < 100; i++) {
      Optional<Node> optional = loadBalance.select();
      log.info("key={},node={}", i, optional.get().getId());
    }

    log.info("-------------------");

    loadBalance.removeById("1");
    for (int i = 0; i < 100; i++) {
      Optional<Node> optional = loadBalance.select();
      log.info("key={},node={}", i, optional.get().getId());
    }

  }


  @Test
  public void RandomTest() throws Exception {
    LoadBalance loadBalance = new RandomLoadBalance(nodes);
    for (int i = 0; i < 100; i++) {
      Optional<Node> optional = loadBalance.select();
      log.info("key={},node={}", i, optional.get().getId());
    }
  }

  @Test
  public void consistentHashTest() throws Exception {
    LoadBalance loadBalance = new ConsistentHashLoadBalance(nodes, 100);

    for (int i = 0; i < 100; i++) {
      Optional<Node> optional = loadBalance.select("" + i);
      log.info("key={},node={}", i, optional.get().getId());
    }
  }
}
