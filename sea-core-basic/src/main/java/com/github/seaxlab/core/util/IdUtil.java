package com.github.seaxlab.core.util;

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
     * yyyyMMdd
     *
     * @return
     */
    public static String getYYYYMMDD() {
        return DateUtil.toString(new Date(), DateUtil.DAY_FORMAT2);
    }

    public static String getYYYYMMDDHHMM() {
        return DateUtil.toString(new Date(), "yyyyMMddHHmm");
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


    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    /**
     * short id
     *
     * @return
     */
    public static String getSimpleId() {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();

    }

}
