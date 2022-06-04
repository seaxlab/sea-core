package com.github.spy.sea.core.spring.component.json.annotation;

import com.github.spy.sea.core.spring.component.json.JsonParamFieldConvertor;

import java.lang.annotation.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

    /**
     * 是否启用Json映射，默认true
     *
     * @return
     */
    boolean enable() default true;

    /**
     * 是否从根部查账，默认true
     *
     * @return
     */
    boolean root() default true;

    /**
     * 参数映射路径
     *
     * @return
     */
    String path() default "";

    /**
     * 是否必选
     *
     * @return
     */
    boolean required() default false;

    /**
     * 参数默认值
     *
     * @return
     */
    String defaultValue() default "";

    /**
     * 是否处理脚本注入
     *
     * @return
     */
    boolean xss() default true;

    /**
     * json参数处理器
     *
     * @return
     */
    Class<? extends JsonParamFieldConvertor<?, ?>>[] convertor() default {};

    /**
     * Map结构中处理参数名称，配合JsonParamFieldConvertor使用
     *
     * @return
     */
    String[] handleFieldName() default "";

}
