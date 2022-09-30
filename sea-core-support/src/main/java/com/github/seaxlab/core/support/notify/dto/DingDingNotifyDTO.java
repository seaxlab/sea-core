package com.github.seaxlab.core.support.notify.dto;

import com.github.seaxlab.core.support.notify.manager.dingding.DingDingMsgTypeEnum;
import lombok.Data;

import java.util.ArrayList;
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

    private DingDingMsgTypeEnum msgTypeEnum;
    // at one/all
    private At at;


    @Data
    public static class At {

        private List<String> atMobiles;
        private boolean atAll;

        //extend
        public void addMobile(String mobile) {
            if (atMobiles == null) {
                atMobiles = new ArrayList<>();
            }
            if (mobile == null || mobile.trim().isEmpty()) {
                return;
            }

            atMobiles.add(mobile);
        }
    }
}
