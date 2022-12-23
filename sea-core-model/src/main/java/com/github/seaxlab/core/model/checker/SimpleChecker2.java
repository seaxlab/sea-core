package com.github.seaxlab.core.model.checker;

/**
 * simple checker2
 * <p>适用于校验中直接抛出业务异常场景</p>
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
public interface SimpleChecker2<Input1, Input2> {

  /**
   * check input args
   *
   * @param input1
   * @param input2
   */
  void check(Input1 input1, Input2 input2);
}
