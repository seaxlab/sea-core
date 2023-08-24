package com.github.seaxlab.core.component.template.manager;

/**
 * one unit2 manager
 *
 * @author spy
 * @version 1.0 2023/08/24
 * @since 1.0
 */
public interface OneUnit2Manager<I, R> {

  R execute(I bo);

  R executeInNestedTx(I bo);

}
