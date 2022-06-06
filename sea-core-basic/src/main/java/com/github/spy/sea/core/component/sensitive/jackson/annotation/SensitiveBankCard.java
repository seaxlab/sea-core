package com.github.spy.sea.core.component.sensitive.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.github.spy.sea.core.component.sensitive.strategy.SensitiveChineseNameStrategy;

import java.lang.annotation.*;

/**
 * 银行卡号脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveChineseNameStrategy.class, pattern = "(?<=\\w{4})\\w(?=\\w{4})", replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveBankCard {

}
