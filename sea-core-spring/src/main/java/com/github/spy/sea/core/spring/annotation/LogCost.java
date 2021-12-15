package com.github.spy.sea.core.spring.annotation;

import java.lang.annotation.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/15
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogCost {

    String remark() default "";

}
