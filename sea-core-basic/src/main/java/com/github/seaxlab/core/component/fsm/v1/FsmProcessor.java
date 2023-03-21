package com.github.seaxlab.core.component.fsm.v1;

import com.github.seaxlab.core.exception.BaseAppException;

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
    T action(FsmContext context) throws BaseAppException;
}
