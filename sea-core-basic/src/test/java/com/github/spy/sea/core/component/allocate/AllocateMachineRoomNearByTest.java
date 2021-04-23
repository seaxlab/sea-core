/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spy.sea.core.component.allocate;

import com.github.spy.sea.core.component.allocate.impl.AllocateAveragely;
import com.github.spy.sea.core.component.allocate.impl.AllocateMachineRoomNearby;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class AllocateMachineRoomNearByTest {

    private static final String CID_PREFIX = "CID-";

    private final String topic = "topic_test";
    private final AllocateMachineRoomNearby.MachineRoomResolver machineRoomResolver = new AllocateMachineRoomNearby.MachineRoomResolver() {
        @Override
        public String brokerDeployIn(Node messageQueue) {
            return messageQueue.getBrokerName().split("-")[0];
        }

        @Override
        public String consumerDeployIn(String clientID) {
            return clientID.split("-")[0];
        }
    };
    private final AllocateStrategy allocateStrategy = new AllocateMachineRoomNearby(new AllocateAveragely(), machineRoomResolver);


    @Before
    public void init() {
    }


    @Test
    public void test1() {
        testWhenIDCSizeEquals(5, 20, 10, false);
        testWhenIDCSizeEquals(5, 20, 20, false);
        testWhenIDCSizeEquals(5, 20, 30, false);
        testWhenIDCSizeEquals(5, 20, 0, false);
    }

    @Test
    public void test2() {
        testWhenConsumerIDCIsMore(5, 1, 10, 10, false);
        testWhenConsumerIDCIsMore(5, 1, 10, 5, false);
        testWhenConsumerIDCIsMore(5, 1, 10, 20, false);
        testWhenConsumerIDCIsMore(5, 1, 10, 0, false);
    }

    @Test
    public void test3() {
        testWhenConsumerIDCIsLess(5, 2, 10, 10, false);
        testWhenConsumerIDCIsLess(5, 2, 10, 5, false);
        testWhenConsumerIDCIsLess(5, 2, 10, 20, false);
        testWhenConsumerIDCIsLess(5, 2, 10, 0, false);
    }


    @Test
    public void testRun10RandomCase() {
        for (int i = 0; i < 10; i++) {
            int consumerSize = new Random().nextInt(200) + 1;//1-200
            int queueSize = new Random().nextInt(100) + 1;//1-100
            int brokerIDCSize = new Random().nextInt(10) + 1;//1-10
            int consumerIDCSize = new Random().nextInt(10) + 1;//1-10

            if (brokerIDCSize == consumerIDCSize) {
                testWhenIDCSizeEquals(brokerIDCSize, queueSize, consumerSize, false);
            } else if (brokerIDCSize > consumerIDCSize) {
                testWhenConsumerIDCIsLess(brokerIDCSize, brokerIDCSize - consumerIDCSize, queueSize, consumerSize, false);
            } else {
                testWhenConsumerIDCIsMore(brokerIDCSize, consumerIDCSize - brokerIDCSize, queueSize, consumerSize, false);
            }
        }
    }


    public void testWhenIDCSizeEquals(int IDCSize, int queueSize, int consumerSize, boolean print) {
        if (print) {
            System.out.println("Test : IDCSize = " + IDCSize + "queueSize = " + queueSize + " consumerSize = " + consumerSize);
        }
        List<String> cidAll = prepareConsumer(IDCSize, consumerSize);
        List<Node> mqAll = prepareMQ(IDCSize, queueSize);
        List<Node> resAll = new ArrayList<Node>();
        for (String currentID : cidAll) {
            List<Node> res = allocateStrategy.allocate("Test-C-G", currentID, mqAll, cidAll);
            if (print) {
                System.out.println("cid: " + currentID + "--> res :" + res);
            }
            for (Node mq : res) {
                Assert.assertTrue(machineRoomResolver.brokerDeployIn(mq).equals(machineRoomResolver.consumerDeployIn(currentID)));
            }
            resAll.addAll(res);
        }
        Assert.assertTrue(hasAllocateAllQ(cidAll, mqAll, resAll));

        if (print) {
            System.out.println("-------------------------------------------------------------------");
        }
    }

    public void testWhenConsumerIDCIsMore(int brokerIDCSize, int consumerMore, int queueSize, int consumerSize, boolean print) {
        if (print) {
            System.out.println("Test : IDCSize = " + brokerIDCSize + " queueSize = " + queueSize + " consumerSize = " + consumerSize);
        }
        Set<String> brokerIDCWithConsumer = new TreeSet<String>();
        List<String> cidAll = prepareConsumer(brokerIDCSize + consumerMore, consumerSize);
        List<Node> mqAll = prepareMQ(brokerIDCSize, queueSize);
        for (Node mq : mqAll) {
            brokerIDCWithConsumer.add(machineRoomResolver.brokerDeployIn(mq));
        }

        List<Node> resAll = new ArrayList<Node>();
        for (String currentID : cidAll) {
            List<Node> res = allocateStrategy.allocate("Test-C-G", currentID, mqAll, cidAll);
            if (print) {
                System.out.println("cid: " + currentID + "--> res :" + res);
            }
            for (Node mq : res) {
                if (brokerIDCWithConsumer.contains(machineRoomResolver.brokerDeployIn(mq))) {//healthy idc, so only consumer in this idc should be allocated
                    Assert.assertTrue(machineRoomResolver.brokerDeployIn(mq).equals(machineRoomResolver.consumerDeployIn(currentID)));
                }
            }
            resAll.addAll(res);
        }

        Assert.assertTrue(hasAllocateAllQ(cidAll, mqAll, resAll));
        if (print) {
            System.out.println("-------------------------------------------------------------------");
        }
    }

    public void testWhenConsumerIDCIsLess(int brokerIDCSize, int consumerIDCLess, int queueSize, int consumerSize, boolean print) {
        if (print) {
            System.out.println("Test : IDCSize = " + brokerIDCSize + " queueSize = " + queueSize + " consumerSize = " + consumerSize);
        }
        Set<String> healthyIDC = new TreeSet<String>();
        List<String> cidAll = prepareConsumer(brokerIDCSize - consumerIDCLess, consumerSize);
        List<Node> mqAll = prepareMQ(brokerIDCSize, queueSize);
        for (String cid : cidAll) {
            healthyIDC.add(machineRoomResolver.consumerDeployIn(cid));
        }

        List<Node> resAll = new ArrayList<Node>();
        Map<String, List<Node>> idc2Res = new TreeMap<String, List<Node>>();
        for (String currentID : cidAll) {
            String currentIDC = machineRoomResolver.consumerDeployIn(currentID);
            List<Node> res = allocateStrategy.allocate("Test-C-G", currentID, mqAll, cidAll);
            if (print) {
                System.out.println("cid: " + currentID + "--> res :" + res);
            }
            if (!idc2Res.containsKey(currentIDC)) {
                idc2Res.put(currentIDC, new ArrayList<Node>());
            }
            idc2Res.get(currentIDC).addAll(res);
            resAll.addAll(res);
        }

        for (String consumerIDC : healthyIDC) {
            List<Node> resInOneIDC = idc2Res.get(consumerIDC);
            List<Node> mqInThisIDC = createMessageQueueList(consumerIDC, queueSize);
            Assert.assertTrue(resInOneIDC.containsAll(mqInThisIDC));
        }

        Assert.assertTrue(hasAllocateAllQ(cidAll, mqAll, resAll));
        if (print) {
            System.out.println("-------------------------------------------------------------------");
        }
    }


    private boolean hasAllocateAllQ(List<String> cidAll, List<Node> mqAll, List<Node> allocatedResAll) {
        if (cidAll.isEmpty()) {
            return allocatedResAll.isEmpty();
        }
        return mqAll.containsAll(allocatedResAll) && allocatedResAll.containsAll(mqAll) && mqAll.size() == allocatedResAll.size();
    }


    private List<String> createConsumerIdList(String machineRoom, int size) {
        List<String> consumerIdList = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            consumerIdList.add(machineRoom + "-" + CID_PREFIX + String.valueOf(i));
        }
        return consumerIdList;
    }

    private List<Node> createMessageQueueList(String machineRoom, int size) {
        List<Node> messageQueueList = new ArrayList<Node>(size);
        for (int i = 0; i < size; i++) {
            Node mq = new Node(topic, machineRoom + "-brokerName", i);
            messageQueueList.add(mq);
        }
        return messageQueueList;
    }

    private List<Node> prepareMQ(int brokerIDCSize, int queueSize) {
        List<Node> mqAll = new ArrayList<Node>();
        for (int i = 1; i <= brokerIDCSize; i++) {
            mqAll.addAll(createMessageQueueList("IDC" + i, queueSize));
        }

        return mqAll;
    }

    private List<String> prepareConsumer(int IDCSize, int consumerSize) {
        List<String> cidAll = new ArrayList<String>();
        for (int i = 1; i <= IDCSize; i++) {
            cidAll.addAll(createConsumerIdList("IDC" + i, consumerSize));
        }
        return cidAll;
    }
}
