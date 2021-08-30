package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Slf4j
public class DateTimeContext {

    public static SimpleDateFormat get(String format) {
        String key = getKey(format);
        SimpleDateFormat sdf = ThreadContext.get(key);
        if (sdf == null) {
            sdf = new SimpleDateFormat(format);
            ThreadContext.put(key, sdf);
        }

        return sdf;
    }

    public static void clean(String format) {
        ThreadContext.remove(getKey(format));
    }

    /**
     * inner key.
     *
     * @param format date time format key.
     * @return String
     */
    private static String getKey(String format) {
        Precondition.checkNotEmpty(format, "format cannot be empty");
        return "sea_sdf_" + format;
    }

}
