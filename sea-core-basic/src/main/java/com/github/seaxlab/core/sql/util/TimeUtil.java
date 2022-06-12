package com.github.seaxlab.core.sql.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/26
 * @since 1.0
 */
@Slf4j
public final class TimeUtil {

    public static Time of(Date date) {
        if (date == null) {
            return null;
        }
        return new Time(date.getTime());
    }
}
