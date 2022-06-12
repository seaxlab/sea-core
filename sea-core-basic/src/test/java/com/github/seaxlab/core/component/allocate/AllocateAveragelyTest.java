package com.github.seaxlab.core.component.allocate;

import com.github.seaxlab.core.component.allocate.impl.AllocateAveragely;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/24
 * @since 1.0
 */
@Slf4j
public class AllocateAveragelyTest extends AllocateStrategyTest {

    @Test
    public void testAllocate() throws Exception {

        // 平均分配
        AllocateStrategy strategy = new AllocateAveragely();
        String topic = "topic_test";
        String currentCID = "CID";
        int queueSize = 19;
        int consumerSize = 5;

        List<Node> mqAll = new ArrayList<>();
        for (int i = 0; i < queueSize; i++) {
            Node mq = new Node(topic, "brokerName", i);
            mqAll.add(mq);
        }
        // consumer id.
        List<String> cidAll = new ArrayList<>();
        for (int j = 0; j < consumerSize; j++) {
            cidAll.add("CID" + j);
        }

        log.info("nodes={}", mqAll.stream().map(item -> item.getNodeId()).collect(Collectors.toList()));
        log.info("consumer ids={}", cidAll);

        for (int i = 0; i < consumerSize; i++) {
            List<Node> rs = strategy.allocate("", currentCID + i, mqAll, cidAll);
            log.info("rs[{}]:{}", currentCID + i, rs.stream().map(item -> item.getNodeId()).collect(Collectors.toList()));
        }
    }
}
