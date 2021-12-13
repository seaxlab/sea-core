package com.github.spy.sea.core.support.oss.manager.impl;

import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import lombok.extern.slf4j.Slf4j;

/**
 * tencent oss manager
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public class TencentOssManager extends AbstractOssManager {
    //TODO


    @Override
    public void init(OssConfig config) {
        super.init(config);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public String getType() {
        return OssTypeEnum.TENCENT_CLOUD.getCode();
    }
}
