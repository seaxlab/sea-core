package com.github.seaxlab.core.component.sensitive.fastjson.annotation;

import com.github.seaxlab.core.component.sensitive.strategy.DefaultSensitiveStrategy;
import com.github.seaxlab.core.component.sensitive.strategy.IStrategy;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SensitiveInfo {


    /**
     * 脱敏策略
     *
     * @return
     */
    Class<? extends IStrategy> strategy() default DefaultSensitiveStrategy.class;

    /**
     * 输入格式，使用正则表达式, 优先级大于value
     *
     * @return 格式
     */
    String pattern() default "";

    /**
     * 替换目标字符, 优先级大于value
     *
     * @return 替换目标字符串
     */
    String replaceChar() default "";

    /**
     * 开始显示几位
     *
     * @return
     */
    int begin() default 0;

    /**
     * 结束显示几位
     *
     * @return
     */
    int end() default 0;
}
