package com.github.seaxlab.core.enums;

import java.io.Serializable;

/**
 * 状态枚举标准定义
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
public interface IBaseEnum<T> extends Serializable {
    /**
     * 标准编码
     *
     * @return
     */
    T getCode();

    /**
     * 描述
     *
     * @return
     */
    String getDesc();
}
