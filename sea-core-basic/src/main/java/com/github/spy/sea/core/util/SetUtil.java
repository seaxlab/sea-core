package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * set util
 *
 * @author spy
 * @version 1.0 2019-08-14
 * @since 1.0
 */
@Slf4j
public final class SetUtil {

    private SetUtil() {
    }


    /**
     * empty set
     *
     * @return
     */
    public static Set empty() {
        return Collections.EMPTY_SET;
    }

    /**
     * list to set
     *
     * @param list
     * @return
     */
    public static Set toSet(List list) {
        return new HashSet(list);
    }

    /**
     * array to set
     *
     * @param arrays
     * @return
     */
    public static Set toSet(Object[] arrays) {
        return new HashSet(Arrays.asList(arrays));
    }

}
