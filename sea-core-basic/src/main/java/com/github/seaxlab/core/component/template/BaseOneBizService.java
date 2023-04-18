package com.github.seaxlab.core.component.template;

/**
 * base one biz service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
public abstract class BaseOneBizService<I, R> implements OneBizService<I, R> {

  @Override
  public R execute(I bo) {
    return null;
  }
}
