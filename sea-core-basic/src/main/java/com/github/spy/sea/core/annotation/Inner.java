package com.github.spy.sea.core.annotation;

import java.lang.annotation.*;

/**
 * 实验中的特性或方法
 *
 * @author spy
 * @version 1.0 2020/10/30
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Inner {
}
