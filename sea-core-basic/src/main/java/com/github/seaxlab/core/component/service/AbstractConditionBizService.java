package com.github.seaxlab.core.component.service;

import com.github.seaxlab.core.component.service.lifecycle.AbstractDestroyLifeCycle;
import com.github.seaxlab.core.component.service.lifecycle.AbstractExecuteLifeCycle;
import com.github.seaxlab.core.component.service.lifecycle.AbstractValidateLifeCycle;
import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public abstract class AbstractConditionBizService<Void>
        implements AbstractService, AbstractValidateLifeCycle,
        AbstractExecuteLifeCycle, AbstractDestroyLifeCycle {

    /**
     * 返回值
     *
     * @return
     */
    abstract <T> Result<T> doService();

    /**
     * @return
     */
    abstract Result success();

    /**
     * @return
     */
    abstract Result failure();


    @Override
    public Result<Void> execute() {


        return null;
    }
}
