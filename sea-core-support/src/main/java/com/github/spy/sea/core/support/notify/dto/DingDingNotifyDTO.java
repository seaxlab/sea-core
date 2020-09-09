package com.github.spy.sea.core.support.notify.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */
@Data
public class DingDingNotifyDTO extends BaseNotifyDTO {
    // at one/all
    private At at;

    /**
     * at
     *
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
