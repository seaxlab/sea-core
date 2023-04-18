package com.github.seaxlab.core.component.template;

/**
 * one biz service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
public interface OneBizService<I, R> {

  R execute(I bo);

}
