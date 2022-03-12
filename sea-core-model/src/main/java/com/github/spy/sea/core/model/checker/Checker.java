package com.github.spy.sea.core.model.checker;

import com.github.spy.sea.core.model.Result;

/**
 * simple checker
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
public interface Checker<I, R> {


    /**
     * 校验器
     *
     * @param input
     * @return
     */
    Result<R> check(I input);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
