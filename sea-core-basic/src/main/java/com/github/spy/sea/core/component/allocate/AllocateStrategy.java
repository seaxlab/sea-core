package com.github.spy.sea.core.component.allocate;

import java.util.List;

/**
 * Strategy Algorithm for message allocating between consumers
 *
 * @author spy
 * @version 1.0 2021/4/24
 * @since 1.0
 */
public interface AllocateStrategy {
    /**
     * Allocating by consumer id
     *
     * @param consumerGroup current consumer group
     * @param currentCID    current consumer id
     * @param nodeAll       node set in current topic
     * @param cidAll        consumer set in current consumer group
     * @return
     */
    public List<Node> allocate(//
                               final String consumerGroup,//
                               final String currentCID,//
                               final List<Node> nodeAll,//
                               final List<String> cidAll//
    );


    /**
     * Algorithm name
     *
     * @return
     */
    public String getName();

}
