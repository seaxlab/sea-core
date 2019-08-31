package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class DiffUtil {

    public static Diff compare(Object leftObj, Object rightObj) {
        return JaversBuilder.javers()
                            .build()
                            .compare(leftObj, rightObj);
    }
}
