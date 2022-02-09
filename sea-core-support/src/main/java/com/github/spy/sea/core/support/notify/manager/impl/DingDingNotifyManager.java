package com.github.spy.sea.core.support.notify.manager.impl;

import com.github.spy.sea.core.http.simple.HttpClientUtil;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.notify.dto.DingDingNotifyDTO;
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
public class DingDingNotifyManager implements NotifyManager<DingDingNotifyDTO> {

    @Getter
    @Setter
    private String endpoint;

    @Override
    public BaseResult send(DingDingNotifyDTO dto) {
        BaseResult result = BaseResult.fail();
        try {
            send0(dto);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("send ding ding msg error", e);
            result.setErrorMessage(e.getMessage());
        }

        return result;
    }

    private void send0(DingDingNotifyDTO dto) {
        log.info("send ding ding msg begin.");
        log.debug("title={},msg={}", dto.getTitle(), dto.getContent());
        DingDingRobotSendRequest request = new DingDingRobotSendRequest();

        if (dto.getMsgTypeEnum() == null) {
            dto.setMsgTypeEnum(DingDingMsgTypeEnum.TEXT);
        }
        request.setMsgtype(dto.getMsgTypeEnum().getKey());

        switch (dto.getMsgTypeEnum()) {
            case TEXT:
                DingDingRobotSendRequest.Text text = new DingDingRobotSendRequest.Text();
                text.setContent(dto.getContent());

                request.setText(text);
                break;
            case MARKDOWN:

                DingDingRobotSendRequest.Markdown markdown = new DingDingRobotSendRequest.Markdown();
                markdown.setTitle(dto.getTitle());
                markdown.setText(dto.getContent());

                request.setMarkdown(markdown);
                break;
            default:
                log.warn("unsupported dingding msg type={}", dto.getMsgTypeEnum());
                break;
        }


        // check at
        if (dto.getAt() != null) {
            DingDingNotifyDTO.At at = dto.getAt();

            DingDingRobotSendRequest.At finalAt = new DingDingRobotSendRequest.At();
            if (at.getAtMobiles() != null) {
                finalAt.setAtMobiles(at.getAtMobiles());
            }
            if (at.getIsAtAll()) {
                finalAt.setIsAtAll(true);
            }
            request.setAt(finalAt);
        }


        String response = HttpClientUtil.postJSON(endpoint, request);
        log.info("send ding ding msg end, response={}.", response);
    }
}
