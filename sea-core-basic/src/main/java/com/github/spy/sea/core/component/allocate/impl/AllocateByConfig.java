package com.github.spy.sea.core.component.allocate.impl;


import com.github.spy.sea.core.component.allocate.AllocateStrategy;
import com.github.spy.sea.core.component.allocate.Node;

import java.util.List;

public class AllocateByConfig implements AllocateStrategy {
    private List<Node> nodeList;

    @Override
    public List<Node> allocate(String consumerGroup, String currentCID, List<Node> nodeAll, List<String> cidAll) {
        return this.nodeList;
    }

    @Override
    public String getName() {
        return "CONFIG";
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
