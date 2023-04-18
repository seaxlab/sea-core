package com.github.seaxlab.core.component.template;

/**
 * simple checker
 * <p>适用于校验中直接抛出业务异常场景</p>
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
public interface Checker<I> {

  /**
   * check
   *
   * @param input
   */
  void check(I input);
}
