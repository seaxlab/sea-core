package com.github.seaxlab.core.component.template.manager;

/**
 * one unit1 manager
 *
 * @author spy
 * @version 1.0 2023/08/24
 * @since 1.0
 */
public interface OneUnit1Manager<I> {

  void execute(I bo);

  void executeInNestedTx(I bo);
}
