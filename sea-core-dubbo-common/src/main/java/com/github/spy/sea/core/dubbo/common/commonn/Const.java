package com.github.spy.sea.core.dubbo.common.commonn;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public class Const {

    public static final String DEFAULT_APP_NAME = "sea-core-dubbo";

    public static final String DEFAULT_VERSION = "";
    // 默认超时时间
    public static final Integer DEFAULT_TIME_OUT = 30 * 1000;
    // 默认重试次数
    public static final Integer DEFAULT_RETRY = 2;
}
