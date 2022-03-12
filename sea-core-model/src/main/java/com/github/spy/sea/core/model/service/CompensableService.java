package com.github.spy.sea.core.model.service;

import com.github.spy.sea.core.model.Result;

/**
 * 反向操作，可补偿的
 *
 * @author spy
 * @version 1.0 2021/6/7
 * @since 1.0
 */
public interface CompensableService<Input, R> {

    Result<R> compensate(Input input);
}
