package com.github.spy.sea.core.component.loadbalance.impl;

import com.github.spy.sea.core.component.loadbalance.AbstractLoadBalance;
import com.github.spy.sea.core.component.loadbalance.model.Node;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public class RandomLoadBalance extends AbstractLoadBalance {
    private final List<Node> nodes;

    public RandomLoadBalance(List<Node> nodes) {
        Preconditions.checkNotNull(nodes, "nodes cannot be null.");
        this.nodes = nodes;
    }

    @Override
    public Optional<Node> select() {
        return select(StringUtil.EMPTY);
    }

    @Override
    public Optional<Node> select(String key) {
        int offset = ThreadLocalRandom.current().nextInt(nodes.size());
        return Optional.of(nodes.get(offset));
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

}
