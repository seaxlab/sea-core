package com.github.spy.sea.core.spring.component.json.xss;

/**
 * 字段替换器
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@FunctionalInterface
public interface FieldReplacer {

    /**
     * 替换内容
     *
     * @param old 原始值
     * @return 替换值
     */
    Object doReplace(Object old);
}
