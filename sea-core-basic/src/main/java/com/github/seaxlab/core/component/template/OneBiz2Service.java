package com.github.seaxlab.core.component.template;

/**
 * one biz service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
public interface OneBiz2Service<I, R> {

  R execute(I bo);

}
