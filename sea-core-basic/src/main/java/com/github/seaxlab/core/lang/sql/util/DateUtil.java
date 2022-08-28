package com.github.seaxlab.core.lang.sql.util;

import lombok.extern.slf4j.Slf4j;


/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/26
 * @since 1.0
 */
@Slf4j
public final class DateUtil {

    private DateUtil() {
    }

    /**
     * to sql date
     *
     * @param date
     * @return
     */
    public static java.sql.Date of(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

}
