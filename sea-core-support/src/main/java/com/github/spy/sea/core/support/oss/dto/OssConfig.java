package com.github.spy.sea.core.support.oss.dto;

import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Data
public class OssConfig {

    private String endpoint;

    private String accessKey;

    private String secretKey;


    // 非标准参数
    private Map<String, String> extra;

    public void addExtra(String key, String value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        if (extra == null) {
            extra = new HashMap<>();
        }
        this.extra.put(key, value);
    }
}
