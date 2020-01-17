package com.github.spy.sea.core.rocketmq.common;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CommonRocketMqConsumer {

    private DefaultMQPushConsumer consumer;

    private String topicId;

    private String namesrvAddr;

    private String consumerId;

    MessageListenerConcurrently messageListener;

    private Boolean broadCastFlag;

    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerId);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setVipChannelEnabled(false);
        consumer.subscribe(topicId, "*");
        consumer.setPullBatchSize(1);
        if (broadCastFlag != null && broadCastFlag) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
            log.info("rocket mq message model={}", MessageModel.BROADCASTING.getModeCN());
        }
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

    public Boolean getBroadCastFlag() {
        return broadCastFlag;
    }

    public void setBroadCastFlag(Boolean broadCastFlag) {
        this.broadCastFlag = broadCastFlag;
    }
}
