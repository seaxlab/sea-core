package com.github.spy.sea.core.component.graph;

import com.github.spy.sea.core.BaseCoreTest;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/1/18
 * @since 1.0
 */
@Slf4j
public class GuavaGraphTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        MutableGraph<String> graph = GraphBuilder.directed()
                                                 //.allowsSelfLoops(true)
                                                 .build();

        graph.putEdge("a", "b");
        graph.putEdge("b", "c");
        graph.putEdge("c", "a");
        graph.putEdge("c", "d");
        graph.putEdge("d", "e");
        graph.putEdge("e", "c");
        log.info("cycles={}", Graphs.hasCycle(graph));


    }

}
