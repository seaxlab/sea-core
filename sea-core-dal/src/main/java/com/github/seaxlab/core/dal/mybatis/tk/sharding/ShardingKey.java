package com.github.seaxlab.core.dal.mybatis.tk.sharding;

import com.github.seaxlab.core.dal.mybatis.common.enums.ShardingEnum;

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

    /**
     * 分库策略
     *
     * @return sharding enum
     */
    ShardingEnum strategy() default ShardingEnum.MOD;
}
