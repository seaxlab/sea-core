package com.github.spy.sea.core.component.fsm.v1;

import com.github.spy.sea.core.exception.BaseAppException;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
@Slf4j
public abstract class AbstractFsmProcessor<T> implements FsmProcessor<T>, FsmActionStep<T> {

    //TODO 使用注入式
    @Resource
    private CheckerExecutor checkerExecutor;

    @Override
    public final BaseResult<T> action(FsmContext context) throws BaseAppException {
        BaseResult<T> result = null;

        Checkable checkable = this.getCheckable(context);

        try {

            // 参数校验器
            result = checkerExecutor.serialCheck(context, checkable.getParamChecker());
            if (!result.isOk()) {
                return result;
            }
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = checkerExecutor.serialCheck(context, checkable.getSyncChecker());
            if (!result.isOk()) {
                return result;
            }
            // 并行校验器
            result = checkerExecutor.parallelCheck(context, checkable.getAsyncChecker());
            if (!result.isOk()) {
                return result;
            }

            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务逻辑
            result = this.action(context, nextState);
            if (!result.isOk()) {
                return result;
            }
            // 持久化
            result = this.save(context, nextState);
            if (!result.isOk()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        } catch (BaseAppException e) {
            throw e;
        }
    }
}
