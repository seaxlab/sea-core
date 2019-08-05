package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

/**
 * path util
 *
 * @author spy
 * @version 1.0 2019-08-05
 * @since 1.0
 */
@Slf4j
public final class PathUtil {

    /**
     * 组合路径
     *
     * @param first
     * @param more
     * @return
     */
    public static String join(String first, String... more) {
        return Paths.get(first, more).toString();
    }
}
