package com.github.seaxlab.core.component.fsm.v1;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.model.util.ResultUtil;
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
  public final Result<T> action(FsmContext context) throws BaseAppException {
    Result<T> result = null;

    Checkable checkable = this.getCheckable(context);

    try {

      // 参数校验器
      result = checkerExecutor.serialCheck(context, checkable.getParamChecker());
      if (ResultUtil.isFail(result)) {
        return result;
      }
      // 数据准备
      this.prepare(context);
      // 串行校验器
      result = checkerExecutor.serialCheck(context, checkable.getSyncChecker());
      if (ResultUtil.isFail(result)) {
        return result;
      }
      // 并行校验器
      result = checkerExecutor.parallelCheck(context, checkable.getAsyncChecker());
      if (ResultUtil.isFail(result)) {
        return result;
      }

      // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
      String nextState = this.getNextState(context);
      // 业务逻辑
      result = this.action(context, nextState);
      if (ResultUtil.isFail(result)) {
        return result;
      }
      // 持久化
      result = this.save(context, nextState);
      if (ResultUtil.isFail(result)) {
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
