package com.github.spy.sea.core.rocketmq.common;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.github.spy.sea.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CommonBroadCastRocketMqConsumer {

    private DefaultMQPushConsumer consumer;

    private String topicId;

    private String namesrvAddr;

    private String consumerId;

    MessageListenerConcurrently messageListener;

    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerId + "-" + IdUtil.shortUUID());
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setVipChannelEnabled(false);
        consumer.subscribe(topicId, "*");
        consumer.setPullBatchSize(1);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener(messageListener);
        consumer.start();
    }

    public void destroy() {
        consumer.shutdown();
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public MessageListenerConcurrently getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListenerConcurrently messageListener) {
        this.messageListener = messageListener;
    }

}
