package com.github.seaxlab.core.lang.sql.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/26
 * @since 1.0
 */
@Slf4j
public final class TimestampUtil {

    private TimestampUtil() {
    }


    public static Timestamp of(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

}
