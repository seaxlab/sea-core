package com.github.spy.sea.core.script.groovy;

import lombok.Data;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Data
public class GroovyClassDefinition {

    /**
     * 唯一标识
     */
    private String key;

    /**
     * 文本内容
     */
    private String scriptText;

    /**
     * 文本摘要
     */
    private String digest;

    /**
     * 目标类
     */
    private Class<?> clazz;
}
