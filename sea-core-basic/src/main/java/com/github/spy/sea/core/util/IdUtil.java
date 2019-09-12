package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * unique key
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public final class IdUtil {

    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String shortUUID() {
        return UUID().replaceAll("-", "");
    }


    /**
     * simple datetime id
     *
     * @return yyyyMMddHHmmss
     */
    public static String getYYYYMMDDHHMMSS() {
        return DateUtil.toString(new Date(), DateUtil.DATETIME_FORMAT);
    }

    /**
     * simple datetime id
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String getYYYYMMDDHHMMSSSSS() {
        return DateUtil.toString(new Date(), DateUtil.DATETIME_FORMAT2);
    }


}
