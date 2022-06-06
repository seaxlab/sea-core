package com.github.spy.sea.core.component.sensitive.fastjson.annotation;

import com.github.spy.sea.core.component.sensitive.strategy.SensitiveAddressStrategy;

import java.lang.annotation.*;

/**
 * 地址脱敏
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveAddressStrategy.class, pattern = "(.{5}).+(.{4})", replaceChar = "$1*****$2")
public @interface SensitiveAddress {

}
