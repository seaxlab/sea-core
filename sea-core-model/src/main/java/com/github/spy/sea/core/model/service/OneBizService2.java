package com.github.spy.sea.core.model.service;

import com.github.spy.sea.core.model.Result;

/**
 * one biz service
 *
 * @author spy
 * @version 1.0 2021/6/1
 * @since 1.0
 */
public interface OneBizService2<Context, Input, R> {

    /**
     * execute biz service
     *
     * @param ctx   context
     * @param input input
     * @return R
     */
    Result<R> execute(Context ctx, Input input);
}
