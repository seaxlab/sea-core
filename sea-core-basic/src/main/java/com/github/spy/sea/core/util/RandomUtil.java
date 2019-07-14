package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-19
 * @since 1.0
 */
@Slf4j
public final class RandomUtil {


    /**
     * 生成UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    /**
     * 生成不带中划线的UUID
     *
     * @return
     */
    public static String shortUUID() {
        return uuid().replace("-", "");
    }

}
