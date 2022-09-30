package com.github.seaxlab.core.support.notify.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */

@Data
public class DingDingRobotSendRequest implements Serializable {

    private String msgtype;
    private Text text;
    private Markdown markdown;
    private At at;

    @Data
    public static class Text {
        private String content;
    }

    @Data
    public static class Markdown {
        private String title;
        private String text;
    }

    @Data
    public static class At {
        private List<String> atMobiles;

        @JSONField(name = "isAtAll")
        @JsonProperty("isAtAll")
        private Boolean atAll;
    }


}
