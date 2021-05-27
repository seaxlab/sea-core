package com.github.spy.sea.core.enums;

import java.io.Serializable;

/**
 * 状态枚举标准定义
 *
 * @author spy
 * @version 1.0 2021/5/26
 * @since 1.0
 */
public interface IBaseEnum2<T> extends Serializable {

    /**
     * 标准编码
     *
     * @return
     */
    T getCode();

    /**
     * 对外名称
     *
     * @return
     */
    String getName();

    /**
     * 描述
     *
     * @return
     */
    String getDesc();
}
