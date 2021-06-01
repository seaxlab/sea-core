package com.github.spy.sea.core.model.service;

import com.github.spy.sea.core.model.BaseResult;

/**
 * one biz service
 *
 * @author spy
 * @version 1.0 2021/6/1
 * @since 1.0
 */
public interface OneBizService<Context, R> {

    /**
     * execute biz service
     *
     * @param ctx context
     * @return R
     */
    BaseResult<R> execute(Context ctx);
}
