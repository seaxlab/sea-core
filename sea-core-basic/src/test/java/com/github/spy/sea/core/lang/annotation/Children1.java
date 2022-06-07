package com.github.spy.sea.core.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/7
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Parent(name = "123")
public @interface Children1 {
}
