package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * equal util
 *
 * @author spy
 * @version 1.0 2019-05-31
 * @since 1.0
 */
@Slf4j
public final class EqualUtil {

    private EqualUtil() {

    }

    public static Boolean isEq(Long value1, Long value2) {
        if (Objects.nonNull(value1) && Objects.nonNull(value2)) {
            return value1.longValue() == value2.longValue();
        }

        return false;
    }


    public static Boolean isEq(String str1, String str2) {
        return isEq(str1, str2, true);
    }

    public static Boolean isEq(String str1, String str2, boolean caseSensitive) {
        if (Objects.nonNull(str1) && Objects.nonNull(str2)) {
            return caseSensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
        }
        return false;
    }


    public static Boolean isIn(String str1, String... strArray) {
        Preconditions.checkNotNull(str1);
        Preconditions.checkNotNull(strArray);

        for (int i = 0; i < strArray.length; i++) {
            String item = strArray[i];

            if (isEq(str1, item)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isIn(Integer value, Integer... values) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(values);


        for (int i = 0; i < values.length; i++) {
            Integer item = values[i];

            if (item.intValue() == value.intValue()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isIn(Long value, Long... values) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(values);


        for (int i = 0; i < values.length; i++) {
            Long item = values[i];

            if (item.longValue() == value.longValue()) {
                return true;
            }
        }

        return false;
    }

}
