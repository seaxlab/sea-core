package com.github.spy.sea.core.dal.mybatis.tk.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardingKey {

    /**
     * 默认分表策略
     *
     * @return int
     */
    int value() default 1;
}
