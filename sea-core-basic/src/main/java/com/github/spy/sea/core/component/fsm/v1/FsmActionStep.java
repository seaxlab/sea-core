package com.github.spy.sea.core.component.fsm.v1;


import com.github.spy.sea.core.model.BaseResult;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
public interface FsmActionStep<T> {

    Checkable getCheckable(FsmContext context);


    /**
     * 准备数据
     */
    default void prepare(FsmContext context) {
    }

    /**
     * 获取当前状态处理器处理完毕后，所处于的下一个状态
     */
    String getNextState(FsmContext context);

    /**
     * 状态动作方法，主要状态迁移逻辑
     */
    BaseResult<T> action(FsmContext context, String nextState);

    /**
     * 状态数据持久化
     */
    BaseResult<T> save(FsmContext context, String nextState);

    /**
     * 状态迁移成功，持久化后执行的后续处理
     */
    void after(FsmContext context);
}
