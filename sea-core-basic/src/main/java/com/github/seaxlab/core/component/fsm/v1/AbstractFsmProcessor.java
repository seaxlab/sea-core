package com.github.seaxlab.core.component.fsm.v1;

import com.github.seaxlab.core.exception.BaseAppException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * abstract fsm processor
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
  public final T action(FsmContext context) throws BaseAppException {
    T response = null;
    Checkable checkable = this.getCheckable(context);

    try {

      // 参数校验器
      checkerExecutor.serialCheck(context, checkable.getParamChecker());
      // 数据准备
      this.prepare(context);
      // 串行校验器
      checkerExecutor.serialCheck(context, checkable.getSyncChecker());
      // 并行校验器
      checkerExecutor.parallelCheck(context, checkable.getAsyncChecker());

      // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
      String nextState = this.getNextState(context);
      // 业务逻辑
      this.action(context, nextState);
      // 持久化
      this.save(context, nextState);

      // after
      response = this.after(context);
    } finally {

    }

    return response;
  }
}
