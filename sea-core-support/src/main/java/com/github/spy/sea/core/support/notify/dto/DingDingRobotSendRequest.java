package com.github.spy.sea.core.support.notify.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingDingRobotSendRequest implements Serializable {

    /**
     * at
     */
    private String at;


    private String msgtype;

    /**
     * TEXT
     */
    private Text text;


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

    /**
     * @author top auto create
     * @since 1.0, null
     */
    public static class At extends BaseRequestDTO {
        private List<String> atMobiles;
        /**
         *
         */
        private String isAtAll;

        public List<String> getAtMobiles() {
            return this.atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public String getIsAtAll() {
            return this.isAtAll;
        }

        public void setIsAtAll(String isAtAll) {
            this.isAtAll = isAtAll;
        }
    }


}
