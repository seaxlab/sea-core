package com.github.seaxlab.core.component.sensitive.fastjson.annotation;

import com.github.seaxlab.core.component.sensitive.strategy.SensitiveEmailStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
