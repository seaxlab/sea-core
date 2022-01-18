package com.github.spy.sea.core.component.graph;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 * <p>
 * https://github.com/mikemaal/xwork-core/blob/master/src/main/java/com/opensymphony/xwork2/config/providers/CycleDetector.java
 * </p>
 *
 * @author spy
 * @version 1.0 2022/1/18
 * @since 1.0
 */
@Slf4j
public class CycleDetector<T> implements Serializable {

    private static final String marked = "marked";

    private static final String complete = "complete";

    private DirectedGraph<T> graph;

    private Map<T, String> marks;

    private List<T> verticesInCycles;

    public CycleDetector(DirectedGraph<T> graph) {
        this.graph = graph;
        marks = new HashMap<>();
        verticesInCycles = new ArrayList<>();
    }

    public boolean containsCycle() {
        for (T v : graph) {
            // 如果v正在遍历或者遍历完成,不需要进入mark(),因为mark是一个递归调用，使用的是深度优先搜索算法;
            // 这是为了保证1个顶点只会遍历一次
            if (!marks.containsKey(v)) {
                if (mark(v)) {
                    // return true;
                }
            }
        }
        // return false;
        return !verticesInCycles.isEmpty();
    }

    /**
     * 标记
     *
     * @param vertex 顶点
     * @return
     */
    private boolean mark(T vertex) {
        /*
         * return statements commented out for fail slow behavior detect all nodes in cycles instead of just the first one
         */

        List<T> localCycles = new ArrayList<>();
        marks.put(vertex, marked);
        for (T u : graph.edgesFrom(vertex)) {
            if (marks.containsKey(u) && marks.get(u).equals(marked)) {
                localCycles.add(vertex);
                // return true;
            } else if (!marks.containsKey(u)) {
                if (mark(u)) {
                    localCycles.add(vertex);
                    // return true;
                }
            }
        }
        marks.put(vertex, complete);
        // return false;
        verticesInCycles.addAll(localCycles);
        return !localCycles.isEmpty();
    }

    /**
     * 先调用containsCycle，再调用此方法
     *
     * @return
     */
    public List<T> getVerticesInCycles() {
        return verticesInCycles;
    }
}
