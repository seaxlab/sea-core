package com.github.spy.sea.core.support.notify.manager.impl;

import com.github.spy.sea.core.http.simple.HttpClientUtil;
import com.github.spy.sea.core.support.notify.dto.DingDingRobotSendRequest;
import com.github.spy.sea.core.support.notify.manager.NotifyManager;
import com.github.spy.sea.core.support.notify.manager.dingding.DingDingMsgTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
@Slf4j
public class DingDingNotifyManager implements NotifyManager {

    @Getter
    @Setter
    private String endpoint;

    @Override
    public void send(String msg) {
        log.info("send dingding msg begin.");
        log.warn("msg={}", msg);

        DingDingRobotSendRequest request = new DingDingRobotSendRequest();

        request.setMsgtype(DingDingMsgTypeEnum.TEXT.getKey());

        DingDingRobotSendRequest.Text text = new DingDingRobotSendRequest.Text();

        text.setContent(msg);
        request.setText(text);
        try {
            HttpClientUtil.postJSON(endpoint, request);
        } catch (Exception e) {
            log.error("send ding ding msg error", e);
        }

        log.info("send dingding msg end.");
    }

    @Override
    public void send(String title, String msg) {
        log.info("send dingding msg begin.");
        log.warn("title={},msg={}", title, msg);
        DingDingRobotSendRequest request = new DingDingRobotSendRequest();

        request.setMsgtype(DingDingMsgTypeEnum.TEXT.getKey());

        DingDingRobotSendRequest.Text text = new DingDingRobotSendRequest.Text();

        text.setContent(msg);
        request.setText(text);

        try {
            HttpClientUtil.postJSON(endpoint, request);
        } catch (Exception e) {
            log.error("send ding ding msg error", e);
        }

        log.info("send dingding msg end.");
    }
}
