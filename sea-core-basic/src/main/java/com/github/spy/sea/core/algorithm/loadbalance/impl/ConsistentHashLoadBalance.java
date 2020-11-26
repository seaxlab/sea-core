package com.github.spy.sea.core.algorithm.loadbalance.impl;

import com.github.spy.sea.core.algorithm.loadbalance.AbstractLoadBalance;
import com.github.spy.sea.core.algorithm.loadbalance.HashFunction;
import com.github.spy.sea.core.algorithm.loadbalance.model.Node;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public class ConsistentHashLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "consistenthash";

    private final List<Node> nodes;
    private final HashFunction hashFunction;
    //虚拟节点
    private final int numberOfReplicas;
    // 用来存储虚拟节点hash值
    private final SortedMap<Long, Node> circle = new TreeMap<>();

    public ConsistentHashLoadBalance(List<Node> nodes, int numberOfReplicas) {
        this(nodes, numberOfReplicas, new DefaultHashFunction());
    }

    public ConsistentHashLoadBalance(List<Node> nodes, int numberOfReplicas, HashFunction hashFunction) {
        Preconditions.checkNotNull(nodes, "nodes cannot be null.");
        this.nodes = nodes;
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas <= 0 ? 100 : numberOfReplicas;

        for (Node node : this.nodes) {
            add(node);
        }
    }

    /**
     * 获得一个最近的顺时针节点
     *
     * @param key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * @return
     */
    @Override
    public Optional<Node> select(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            //返回此映射的部分视图，其键大于等于
            SortedMap<Long, Node> tailMap = circle.tailMap(hash);
            // hash
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return Optional.of(circle.get(hash));
    }

    @Override
    public void add(Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    @Override
    public void remove(Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }


}
