package com.github.seaxlab.core.support.notify.manager.impl;

import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.notify.dto.FeiShuNotifyDTO;
import com.github.seaxlab.core.support.notify.enums.MsgTypeEnum;
import com.github.seaxlab.core.support.notify.manager.NotifyManager;
import com.github.seaxlab.core.support.notify.util.NotifyUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * feishu notify manager.
 *
 * @author spy
 * @version 1.0 2022/9/30
 * @since 1.0
 */
@Slf4j
public class FeiShuNotifyManager implements NotifyManager<FeiShuNotifyDTO> {

    @Getter
    @Setter
    private String endpoint;

    @Override
    public Result send(FeiShuNotifyDTO dto) {
        Precondition.checkNotBlank(endpoint, "endpoint cannot be empty.");

        Result result = Result.fail();
        try {
            send0(dto);
            result.setSuccess(true);
        } catch (Exception e) {
            log.warn("send fei shu msg error", e);
            result.setMsg(e.getMessage());
        }

        return result;
    }

    private void send0(FeiShuNotifyDTO dto) {
        log.info("send fei shu msg begin.");
        if (log.isDebugEnabled()) {
            log.debug("title={},msg={}", dto.getTitle(), dto.getContent());
        }
        //
        FeiShuNotifyDTO.RobotSendRequest request = new FeiShuNotifyDTO.RobotSendRequest();

        if (dto.getMsgTypeEnum() == null) {
            dto.setMsgTypeEnum(MsgTypeEnum.TEXT);
        }
        request.setMsgType(dto.getMsgTypeEnum().getCode());
        FeiShuNotifyDTO.RobotSendRequest.Content content = new FeiShuNotifyDTO.RobotSendRequest.Content();
        request.setContent(content);
        //
        switch (dto.getMsgTypeEnum()) {
            case TEXT:
                content.setText(NotifyUtil.getContent(dto));
                break;
            case MARKDOWN:
                content.setText(NotifyUtil.getContent(dto));
                break;
            default:
                log.warn("unsupported fei shu msg type={}", dto.getMsgTypeEnum());
                break;
        }


        String response = HttpClientUtil.postJSON(endpoint, request);
        log.info("send fei shu msg end, response={}.", response);
    }

    //{
    //    "msg_type": "text",
    //    "content": {
    //        "text": "新更新提醒"
    //    }
    //}
    //
    // {"StatusCode":0,"StatusMessage":"success"}.


}
