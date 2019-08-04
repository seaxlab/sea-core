package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 唯一key
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public final class IdUtil {

    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String shortUUID() {
        return UUID().replaceAll("-", "");
    }
}
