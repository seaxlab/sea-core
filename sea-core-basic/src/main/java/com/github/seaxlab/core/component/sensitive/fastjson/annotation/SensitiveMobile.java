package com.github.seaxlab.core.component.sensitive.fastjson.annotation;

import com.github.seaxlab.core.component.sensitive.strategy.SensitiveMobileStrategy;

import java.lang.annotation.*;

/**
 * 手机号脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveMobileStrategy.class, pattern = "(?<=\\w{3})\\w(?=\\w{4})", replaceChar = "*")
public @interface SensitiveMobile {

}
