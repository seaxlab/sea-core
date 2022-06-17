package com.github.seaxlab.core.component.sensitive.fastjson.annotation;

import com.github.seaxlab.core.component.sensitive.strategy.SensitiveIdCardStrategy;

import java.lang.annotation.*;

/**
 * 身份证号脱敏
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveIdCardStrategy.class, pattern = "(?<=\\w{0})\\w(?=\\w{4})", replaceChar = "*")
public @interface SensitiveIdCard {

}
