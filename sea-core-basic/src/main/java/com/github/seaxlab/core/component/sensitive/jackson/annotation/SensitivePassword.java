package com.github.seaxlab.core.component.sensitive.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.github.seaxlab.core.component.sensitive.strategy.SensitivePasswordStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 密码脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitivePasswordStrategy.class, pattern = "(?<=).", replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitivePassword {

}
