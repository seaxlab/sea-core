package com.github.spy.sea.core.algorithm.loadbalance;

import com.github.spy.sea.core.algorithm.loadbalance.model.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public Optional<Node> select() {
        return Optional.empty();
    }

    @Override
    public Optional<Node> select(String key) {
        return Optional.empty();
    }

    @Override
    public void add(Node node) {

    }

    @Override
    public void remove(Node node) {

    }

    @Override
    public void removeById(String nodeId) {
        
    }

    /**
     * Calculate the weight according to the uptime proportion of warmup time
     * the new weight will be within 1(inclusive) to weight(inclusive)
     *
     * @param uptime the uptime in milliseconds
     * @param warmup the warmup time in milliseconds
     * @param weight the weight of an invoker
     * @return weight which takes warmup into account
     */
    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        int ww = (int) (uptime / ((float) warmup / weight));
        return ww < 1 ? 1 : (Math.min(ww, weight));
    }
}
