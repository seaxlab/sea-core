package com.github.spy.sea.core.sql.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/26
 * @since 1.0
 */
@Slf4j
public final class DateUtil {
    //TODO 这里应该是sql.date
    public static Date of(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }

}
