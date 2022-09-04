package com.github.seaxlab.core.model.annotation;

import java.lang.annotation.*;

/**
 * 文档注释
 *
 * @author spy
 * @version 1.0 2022/9/1
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Doc {

    String value() default "";
}
