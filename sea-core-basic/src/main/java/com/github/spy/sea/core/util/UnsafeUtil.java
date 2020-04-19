package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe util
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class UnsafeUtil {

    private UnsafeUtil() {
    }

    protected static Unsafe u;

    public static Unsafe getUnsafe() {
        return u;
    }

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);

            u = (Unsafe) f.get(null);
        } catch (Exception ex) {
            log.error("fail to get unsafe class", ex);
            throw new RuntimeException(ex);
        }
    }
}
