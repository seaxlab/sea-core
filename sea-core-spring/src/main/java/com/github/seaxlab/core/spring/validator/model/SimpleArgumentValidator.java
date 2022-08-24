package com.github.seaxlab.core.spring.validator.model;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
public interface SimpleArgumentValidator<T> {

    /**
     * 通过返回true,不通过返回false
     *
     * @param arg 校验参数
     * @return true/false
     */
    boolean check(T arg);
}
