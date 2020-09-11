package com.github.spy.sea.core.model;

/**
 * Base Error Code.
 *
 * @author spy
 * @version 1.0 2020/9/9
 * @since 1.0
 */
public interface IErrorCode {

    /**
     * 错误编码
     *
     * @return
     */
    String getCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getMessage();

    /**
     * 错误类型
     *
     * @return
     */
    int getType();

}
