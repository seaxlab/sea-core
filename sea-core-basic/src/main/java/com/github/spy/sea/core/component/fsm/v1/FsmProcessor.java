package com.github.spy.sea.core.component.fsm.v1;

import com.github.spy.sea.core.exception.BaseAppException;
import com.github.spy.sea.core.model.BaseResult;

/**
 * 状态机处理器接口
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
public interface FsmProcessor<T> {

    /**
     * 执行状态迁移的入口
     */
    BaseResult<T> action(FsmContext context) throws BaseAppException;
}
