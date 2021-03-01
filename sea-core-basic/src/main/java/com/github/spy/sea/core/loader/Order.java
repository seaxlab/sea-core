package com.github.spy.sea.core.loader;

import java.lang.annotation.*;

/**
 * value越小越靠前
 *
 * @author spy
 * @version 1.0 2021/3/1
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Order {

    /**
     * Order int.
     *
     * @return the int
     */
    int value() default 0;
}
