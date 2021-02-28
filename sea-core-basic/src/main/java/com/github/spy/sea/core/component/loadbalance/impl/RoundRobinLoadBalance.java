package com.github.spy.sea.core.component.loadbalance.impl;

import com.github.spy.sea.core.component.loadbalance.AbstractLoadBalance;
import com.github.spy.sea.core.component.loadbalance.model.Node;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "roundrobin";

    private final List<Node> nodes;
    private AtomicLong index = new AtomicLong(0);

    public RoundRobinLoadBalance(List<Node> nodes) {
        Preconditions.checkNotNull(nodes, "nodes cannot be null.");
        this.nodes = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.setWeight(node.getWeight() < 1 ? 1 : node.getWeight());

            if (node.getWeight() == 1) {
                this.nodes.add(node);
                continue;
            }
            // weight based.
            for (int j = 0; j < node.getWeight(); j++) {
                Node innerNode = new Node();
                innerNode.setId(node.getId());
                innerNode.setInnerId(node.getInnerId() + (j + 1));
                innerNode.setWeight(1);
                innerNode.setExtra(node.getExtra());
                this.nodes.add(node);
            }
        }
    }

    @Override
    public Optional<Node> select() {
        return select(StringUtil.EMPTY);
    }

    @Override
    public Optional<Node> select(String key) {
        Long position = index.getAndIncrement() % nodes.size();

        if (index.get() < 0) {
            index.set(0);
        }
        if (position < 0) {
            position = 0L;
        }
        return Optional.of(nodes.get(position.intValue()));
    }


    @Override
    public void add(Node node) {
        synchronized (nodes) {
            nodes.add(node);
        }
    }

    @Override
    public void remove(Node node) {
        synchronized (nodes) {
            nodes.remove(node);
        }
    }

    @Override
    public void removeById(String nodeId) {
        synchronized (nodes) {
            Iterator<Node> it = nodes.iterator();
            while (it.hasNext()) {
                Node node = it.next();
                if (EqualUtil.isEq(node.getId(), nodeId)) {
                    it.remove();
                }
            }
        }
    }
}
