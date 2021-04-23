package com.github.spy.sea.core.rocketmq.core;

import com.alibaba.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import com.alibaba.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.github.spy.sea.core.rocketmq.BaseTest;
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
public class AllocateMessageQueueAveragelyTest extends BaseTest {

    @Test
    public void testAllocate() throws Exception {
        // 平均分配
        AllocateMessageQueueStrategy strategy = new AllocateMessageQueueAveragely();
        String topic = "topic_test";
        String currentCID = "CID";
        int queueSize = 19;
        int consumerSize = 5;

        List<MessageQueue> mqAll = new ArrayList<>();
        for (int i = 0; i < queueSize; i++) {
            MessageQueue mq = new MessageQueue(topic, "brokerName", i);
            mqAll.add(mq);
        }
        // consumer id.
        List<String> cidAll = new ArrayList<>();
        for (int j = 0; j < consumerSize; j++) {
            cidAll.add("CID" + j);
        }

        log.info("message queues={}", mqAll.stream().map(item -> item.getQueueId()).collect(Collectors.toList()));
        log.info("consumer ids={}", cidAll);

        for (int i = 0; i < consumerSize; i++) {
            List<MessageQueue> rs = strategy.allocate("", currentCID + i, mqAll, cidAll);
            log.info("rs[{}]:{}", currentCID + i, rs.stream().map(item -> item.getQueueId()).collect(Collectors.toList()));
        }
    }
}
