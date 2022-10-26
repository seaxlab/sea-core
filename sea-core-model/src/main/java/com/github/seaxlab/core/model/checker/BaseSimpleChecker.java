package com.github.seaxlab.core.model.checker;

/**
 * base simple checker
 * <p>适用于直接抛出业务异常的场景</p>
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
public class BaseSimpleChecker<I> implements SimpleChecker<I> {

    @Override
    public void check(I input) {

    }
}
