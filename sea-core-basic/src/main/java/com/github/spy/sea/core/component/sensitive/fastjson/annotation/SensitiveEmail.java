package com.github.spy.sea.core.component.sensitive.fastjson.annotation;

import com.github.spy.sea.core.component.sensitive.strategy.SensitiveEmailStrategy;

import java.lang.annotation.*;

/**
 * email脱敏
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveEmailStrategy.class, pattern = "(\\w+)\\w{3}@(\\w+)", replaceChar = "$1***@$2")
public @interface SensitiveEmail {

}
