package com.github.seaxlab.core.util;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.util.Map;

/**
 * diff util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public final class DiffUtil {

    /**
     * object diff.
     *
     * @param leftObj
     * @param rightObj
     * @return
     */
    public static Diff compare(Object leftObj, Object rightObj) {
        return JaversBuilder.javers()
                            .build()
                            .compare(leftObj, rightObj);
    }

    /**
     * map diff
     *
     * @param map1
     * @param map2
     * @return
     */
    public static MapDifference<String, Object> compare(Map<String, Object> map1, Map<String, Object> map2) {
        return Maps.difference(map1, map2);
    }
}
