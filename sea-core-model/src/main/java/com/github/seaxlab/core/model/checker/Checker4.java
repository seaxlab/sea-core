package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * simple checker
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
public interface Checker4<I1, I2, I3, I4, R> {


    /**
     * 校验器
     *
     * @param input1 input1 param
     * @param input2 input2 param
     * @param input3 input3 param
     * @param input4 input4 param
     * @return r return type
     */
    Result<R> check(I1 input1, I2 input2, I3 input3, I4 input4);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
