package com.github.seaxlab.core.component.allocate;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/24
 * @since 1.0
 */
@Slf4j
public class Node implements Comparable<Node>, Serializable {
    private String topic;
    private String brokerName;
    private int nodeId;

    public Node() {

    }


    public Node(String topic, String brokerName, int nodeId) {
        this.topic = topic;
        this.brokerName = brokerName;
        this.nodeId = nodeId;
    }


    public String getTopic() {
        return topic;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }


    public String getBrokerName() {
        return brokerName;
    }


    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }


    public int getNodeId() {
        return nodeId;
    }


    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }


    @Override
    public int compareTo(Node o) {
        {
            int result = this.topic.compareTo(o.topic);
            if (result != 0) {
                return result;
            }
        }

        {
            int result = this.brokerName.compareTo(o.brokerName);
            if (result != 0) {
                return result;
            }
        }

        return this.nodeId - o.nodeId;
    }

}
