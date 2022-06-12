package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public final class TimeUnitUtil {

    /**
     * convert duration to ms
     *
     * @param duration
     * @param timeUnit
     * @return
     */
    public static long toMills(long duration, TimeUnit timeUnit) {
        return TimeUnit.MILLISECONDS.convert(duration, timeUnit);
    }
}
