package com.github.spy.sea.core.support.oss.manager.impl;

import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class AwsOssManager extends AbstractOssManager {

    @Override
    public void init(OssConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String getType() {
        return OssTypeEnum.AWS.getCode();
    }

    //TODO
}
