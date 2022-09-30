package com.github.seaxlab.core.support.notify.dto;

import com.github.seaxlab.core.model.layer.dto.BaseRequestDTO;
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

    /**
     * at
     */
    private At at;

    private String msgtype;

    /**
     * TEXT
     */
    private Text text;

    private Markdown markdown;

    /**
     * @author top auto create
     * @since 1.0, null
     */
    public static class Text extends BaseRequestDTO {
        private String content;

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Data
    public static class Markdown {
        private String title;
        private String text;
    }


    /**
     * @author top auto create
     * @since 1.0, null
     */
    public static class At extends BaseRequestDTO {
        private List<String> atMobiles;
        private boolean isAtAll;

        public List<String> getAtMobiles() {
            return this.atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public boolean getIsAtAll() {
            return this.isAtAll;
        }

        public void setIsAtAll(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }
    }


}
