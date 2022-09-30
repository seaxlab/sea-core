package com.github.seaxlab.core.support.notify.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.seaxlab.core.support.notify.enums.MsgTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * fei shu notify DTO
 *
 * @author spy
 * @version 1.0 2022/09/30
 * @since 1.0
 */
@Data
public class FeiShuNotifyDTO extends BaseNotifyDTO {

    private MsgTypeEnum msgTypeEnum;


    @Data
    public static class RobotSendRequest implements Serializable {

        @JSONField(name = "msg_type")
        @JsonProperty("msg_type")
        private String msgType;
        private Content content;

        @Data
        public static class Content {
            private String text;
//            private Markdown markdown;
        }

        @Data
        public static class Text {
            private String text;
        }

        @Data
        public static class Markdown {
            private String title;
            private String text;
        }


    }

}
