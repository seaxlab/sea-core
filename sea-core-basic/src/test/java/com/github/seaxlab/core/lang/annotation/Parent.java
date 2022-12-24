package com.github.seaxlab.core.lang.annotation;

import java.lang.annotation.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/7
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parent {

  String name() default "";
}
