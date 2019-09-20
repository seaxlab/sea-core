package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/28
 * @since 1.0
 */
@Slf4j
public final class CollectionUtil {


    private CollectionUtil() {
    }

    /**
     * check collection is empty
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * check collection is not empty
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * check map is empty
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * check map is not empty
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

}
