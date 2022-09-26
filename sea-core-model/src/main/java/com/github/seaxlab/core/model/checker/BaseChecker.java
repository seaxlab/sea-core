package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * abstract checker
 *
 * @author spy
 * @version 1.0 2022/9/23
 * @since 1.0
 */
public class BaseChecker<I> implements Checker<I> {
    @Override
    public Result<Void> check(I input) {
        return Result.success();
    }
}
