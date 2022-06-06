package com.github.spy.sea.core.component.sensitive.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.github.spy.sea.core.component.sensitive.strategy.SensitiveIdCardStrategy;

import java.lang.annotation.*;

/**
 * 身份证号脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveIdCardStrategy.class, pattern = "(?<=\\w{0})\\w(?=\\w{4})", replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveIdCard {

}
