package com.github.spy.sea.core.component.sensitive.fastjson.annotation;

import com.github.spy.sea.core.component.sensitive.strategy.SensitiveChineseNameStrategy;

import java.lang.annotation.*;

/**
 * 中文名称脱敏
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveChineseNameStrategy.class, pattern = "(?<=.{1}).", replaceChar = "*")
public @interface SensitiveChineseName {


}
