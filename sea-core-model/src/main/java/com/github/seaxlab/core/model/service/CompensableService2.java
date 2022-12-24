package com.github.seaxlab.core.model.service;

import com.github.seaxlab.core.model.Result;

/**
 * 反向操作，可补偿的
 *
 * @author spy
 * @version 1.0 2021/6/7
 * @since 1.0
 */
public interface CompensableService2<Input1, Input2, R> {

  Result<R> compensate(Input1 input1, Input2 input2);
}
