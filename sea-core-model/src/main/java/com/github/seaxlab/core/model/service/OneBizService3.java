package com.github.seaxlab.core.model.service;

import com.github.seaxlab.core.model.Result;

/**
 * one biz service
 *
 * @author spy
 * @version 1.0 2021/6/1
 * @since 1.0
 */
public interface OneBizService3<Context, Input1, Input2, R> {

  /**
   * execute biz service
   *
   * @param ctx    context
   * @param input1 input1
   * @param input2 input2
   * @return R
   */
  Result<R> execute(Context ctx, Input1 input1, Input2 input2);
}
