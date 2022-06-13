package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * simple checker
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
public interface Checker3<I1, I2, I3, R> {


    /**
     * 校验器
     *
     * @param input1 input
     * @param input2 input
     * @param input3 input
     * @return
     */
    Result<R> check(I1 input1, I2 input2, I3 input3);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
