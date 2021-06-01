package com.github.spy.sea.core.model.checker;

import com.github.spy.sea.core.model.BaseResult;

/**
 * simple checker
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
public interface Checker3<Input1, Input2, Input3, R> {


    /**
     * 校验器
     *
     * @param input1 input
     * @param input2 input
     * @param input3 input
     * @return
     */
    BaseResult<R> check(Input1 input1, Input2 input2, Input3 input3);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
