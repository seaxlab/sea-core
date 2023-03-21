package com.github.seaxlab.core.component.fsm.v1;

/**
 * 校验器,直接抛业务异常
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
  void check(FsmContext context);

  /**
   * 多个checker时的执行顺序
   */
  default int order() {
    return 0;
  }
}
