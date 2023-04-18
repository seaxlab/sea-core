package com.github.seaxlab.core.component.template;

/**
 * base simple checker
 * <p>适用于直接抛出业务异常的场景</p>
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
public class BaseChecker2<Input1, Input2> implements Checker2<Input1, Input2> {

  @Override
  public void check(Input1 input1, Input2 input2) {
  }
}
