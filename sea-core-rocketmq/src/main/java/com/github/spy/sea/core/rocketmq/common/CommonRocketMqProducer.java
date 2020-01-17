package com.github.spy.sea.core.rocketmq.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonRocketMqProducer {

    private DefaultMQProducer producer = null;

    private String namesrvAddr;
    private String topicId;
    private String producerId;

    public void init() throws MQClientException {

        producer = new DefaultMQProducer(producerId);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);//.setVipChannel(false);
        producer.start();
    }


    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }


    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public SendResult sendMessage(Object messageBody, String tag) throws Exception {

        Message message = new Message(topicId, tag, JSON.toJSONString(messageBody).getBytes("utf-8"));

        SendResult send = producer.send(message);

        log.info("消息发送成功：{},结果：{}", JSON.toJSONString(messageBody), send);
        return send;
    }
}
