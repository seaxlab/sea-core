package com.github.seaxlab.core.enums;

import java.io.Serializable;

/**
 * base reg exp enum
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
public interface IBaseRegExpEnum<T> extends Serializable {
    /**
     * 表达式
     *
     * @return
     */
    T getExpression();

    /**
     * 描述
     *
     * @return
     */
    String getDesc();
}