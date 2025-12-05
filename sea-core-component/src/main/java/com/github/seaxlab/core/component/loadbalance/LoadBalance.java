package com.github.seaxlab.core.component.loadbalance;

import com.github.seaxlab.core.component.loadbalance.model.Node;

import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
public interface LoadBalance {

    /**
     * 负载均衡算法，无业务键关联
     *
     * @return
     */
    Optional<Node> select();

    /**
     * 负载均衡算法
     *
     * @param key 请求key
     * @return
     */
    Optional<Node> select(String key);

    /**
     * 增加节点
     *
     * @param node
     */
    void add(Node node);

    /**
     * 移除节点
     *
     * @param node
     */
    void remove(Node node);

    /**
     * 根据nodeId移除
     *
     * @param nodeId
     */
    void removeById(String nodeId);
}
