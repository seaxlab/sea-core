package com.github.spy.sea.core.support.oss.enums;

import com.github.spy.sea.core.enums.IBaseEnum;
import com.github.spy.sea.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
@Getter
public enum OssTypeEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "未知"),

    ALI_YUN("ali_yun", "ali yun"),
    QINIU_CLOUD("qiniu_cloud", "qiniu cloud"),
    TENCENT_CLOUD("tencent_cloud", "tencent cloud"),
    MINIO("minio", "minio"),
    HUAWEI_CLOUD("huawei_cloud", "huawei cloud"),
    AWS("aws", "AWS"),

    ;
    //add here
    ;

    private String code;
    private String desc;

    OssTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OssTypeEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (OssTypeEnum item : values()) {
            if (EqualUtil.isEq(code, item.code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }
}