package com.github.seaxlab.core.model.checker;

import com.github.seaxlab.core.model.Result;

/**
 * simple checker
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
public interface Checker<I> {
    
    /**
     * 校验器
     *
     * @param input
     * @return
     */
    Result<Void> check(I input);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
