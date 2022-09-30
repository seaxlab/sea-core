package com.github.seaxlab.core.lang.function;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * <pre>
 *  https://gitee.com/596392912/mica/blob/master/mica-core/src/main/java/net/dreamlu/mica/core/function/CheckedCallable.java
 * </pre>
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@FunctionalInterface
public interface CheckedFunction<T, R> extends Serializable {
    /**
     * Run the Function
     *
     * @param t T
     * @return R R
     * @throws Throwable CheckedException
     */
    @Nullable
    R apply(@Nullable T t) throws Throwable;
}