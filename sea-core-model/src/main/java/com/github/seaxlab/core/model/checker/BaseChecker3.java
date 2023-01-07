package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * abstract checker3
 *
 * @author spy
 * @version 1.0 2022/9/23
 * @since 1.0
 */
public class BaseChecker3<I1, I2, I3> implements Checker3<I1, I2, I3> {
  @Override
  public Result<Void> check(I1 input1, I2 input2, I3 input3) {
    return Result.success();
  }
}
