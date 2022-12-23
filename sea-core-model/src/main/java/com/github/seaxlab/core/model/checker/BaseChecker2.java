package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * abstract checker2
 *
 * @author spy
 * @version 1.0 2022/9/23
 * @since 1.0
 */
public class BaseChecker2<Input1, Input2> implements Checker2<Input1, Input2> {

  @Override
  public Result<Void> check(Input1 input1, Input2 input2) {
    return Result.success();
  }
}
