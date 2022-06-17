package com.github.seaxlab.core.component.graph;

import com.github.seaxlab.core.BaseCoreTest;
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
public class DirectedGraphTest extends BaseCoreTest {

    @Test
    public void testCycle() throws Exception {
        DirectedGraph<String> graph = new DirectedGraph<>();

        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");
        graph.addNode("e");

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "a");
        graph.addEdge("c", "d");
        graph.addEdge("d", "e");
        graph.addEdge("e", "c");

        CycleDetector<String> detector = new CycleDetector<>(graph);

        log.info("contains cycle={},cycles={}", detector.containsCycle(), detector.getVerticesInCycles());
    }

    @Test
    public void testCycle2() throws Exception {
        DirectedGraph<String> graph = new DirectedGraph<>();

        graph.addVector("c", "d");
        graph.addVector("d", "e");
        graph.addVector("e", "c");

        graph.addVector("a", "b");
        graph.addVector("b", "c");
        graph.addVector("c", "a");


        CycleDetector<String> detector = new CycleDetector<>(graph);

        log.info("contains cycle={},cycles={}", detector.containsCycle(), detector.getVerticesInCycles());
    }

    @Test
    public void testCycle3() throws Exception {
        DirectedGraph<String> graph = new DirectedGraph<>();

        graph.addVector("c", "d");
        CycleDetector<String> detector = new CycleDetector<>(graph);

        log.info("contains cycle={},cycles={}", detector.containsCycle(), detector.getVerticesInCycles());
    }


}
