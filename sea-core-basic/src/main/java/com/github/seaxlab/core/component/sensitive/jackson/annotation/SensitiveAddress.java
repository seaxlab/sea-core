package com.github.seaxlab.core.component.sensitive.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.github.seaxlab.core.component.sensitive.strategy.SensitiveAddressStrategy;

import java.lang.annotation.*;

/**
 * 地址脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveAddressStrategy.class, pattern = "(.{5}).+(.{4})", replaceChar = "$1*****$2")
@JacksonAnnotationsInside
public @interface SensitiveAddress {

}
