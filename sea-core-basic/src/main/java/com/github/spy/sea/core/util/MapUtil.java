package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-15
 * @since 1.0
 */
@Slf4j
public final class MapUtil {

    private MapUtil() {
    }

    /**
     * empty map
     *
     * @return
     */
    public static Map empty() {
        return Collections.EMPTY_MAP;
    }

}
