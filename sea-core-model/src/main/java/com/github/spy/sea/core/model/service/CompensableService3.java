package com.github.spy.sea.core.model.service;

import com.github.spy.sea.core.model.BaseResult;

/**
 * 反向操作，可补偿的
 *
 * @author spy
 * @version 1.0 2021/6/7
 * @since 1.0
 */
public interface CompensableService3<Input1, Input2, Input3, R> {

    BaseResult<R> compensate(Input1 input1, Input2 input2, Input3 input3);
}
