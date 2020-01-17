package com.github.spy.sea.core.rocketmq.func.anymethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.github.spy.sea.core.spring.context.SpringContextHolder;
import com.github.spy.sea.core.util.ClassUtil;
import com.github.spy.sea.core.util.ReflectUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/1/17
 * @since 1.0
 */
@Slf4j
public class AnyBeanMethodListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("mq msg list is {}", msgs);
        if (msgs != null) {
            for (int i = 0; i < msgs.size(); i++) {
                try {
                    parseMsg(msgs.get(i));
                } catch (Exception e) {
                    log.error("消费消息失败", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        }
        log.info("msgList is null");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 解析消息
     *
     * @param msgExt
     */
    private void parseMsg(MessageExt msgExt) {

        try {
            AnyBeanMethodMqMsg dto = JSON.toJavaObject(JSON.parseObject(new String(msgExt.getBody(), "utf-8")), AnyBeanMethodMqMsg.class);
            log.info("dto={}", dto);
            if (StringUtil.isEmpty(dto.getBeanName())) {
                return;
            }

            Object bean = SpringContextHolder.getBeanSafe(dto.getBeanName());
            if (bean == null) {
                bean = SpringContextHolder.getBeanSafe(dto.getBeanName() + "Impl");
            }

            if (bean != null && StringUtil.isNotEmpty(dto.getMethod())) {
                ReflectUtil.invokeMethod(bean, dto.getMethod());
                log.info("invoke method={}.{} ", ClassUtil.getClassName(bean), dto.getMethod());
            }
            log.warn("no obj.method execute.");
        } catch (Exception e) {
            log.error("parse mq msg error", e);
        }
    }

}
