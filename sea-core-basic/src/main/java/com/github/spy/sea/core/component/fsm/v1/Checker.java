package com.github.spy.sea.core.component.fsm.v1;

import com.github.spy.sea.core.model.BaseResult;

/**
 * 校验器
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
public interface Checker<T> {

    /**
     * 校验器
     *
     * @param context
     * @return
     */
    BaseResult<T> check(FsmContext context);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
