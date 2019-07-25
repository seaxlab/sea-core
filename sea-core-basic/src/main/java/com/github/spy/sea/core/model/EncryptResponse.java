package com.github.spy.sea.core.model;

import java.lang.annotation.*;

/**
 * 加密返回注解
 * 在类和方法上使用
 * 注意：不能和@ResponseBody一起使用
 *
 * @author spy
 * @version 1.0 2019-07-25
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EncryptResponse {
}
