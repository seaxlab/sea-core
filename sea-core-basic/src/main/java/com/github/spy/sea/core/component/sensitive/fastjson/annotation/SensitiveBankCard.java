package com.github.spy.sea.core.component.sensitive.fastjson.annotation;

import com.github.spy.sea.core.component.sensitive.strategy.SensitiveChineseNameStrategy;

import java.lang.annotation.*;

/**
 * 银行卡脱敏
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = SensitiveChineseNameStrategy.class, pattern = "(?<=\\w{4})\\w(?=\\w{4})", replaceChar = "*")
public @interface SensitiveBankCard {

}
