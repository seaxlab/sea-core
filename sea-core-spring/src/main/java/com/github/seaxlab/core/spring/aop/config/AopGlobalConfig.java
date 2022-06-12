package com.github.seaxlab.core.spring.aop.config;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/23
 * @since 1.0
 */
@Slf4j
public class AopGlobalConfig {

    // 判断是否允许打印耗时日志
    private static boolean logCostFlag = true;

    public static boolean getLogCostFlag() {
        return logCostFlag;
    }

    public static void setLogCostFlag(boolean enable) {
        logCostFlag = enable;
    }

}
