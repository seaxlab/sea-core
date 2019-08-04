package com.github.spy.sea.core.service;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.service.lifecycle.AbstractDestroyLifeCycle;
import com.github.spy.sea.core.service.lifecycle.AbstractExecuteLifeCycle;
import com.github.spy.sea.core.service.lifecycle.AbstractValidateLifeCycle;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单业务逻辑抽象
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public abstract class AbstractSingleConcreteBizService<T>
        implements AbstractService, AbstractValidateLifeCycle, AbstractExecuteLifeCycle, AbstractDestroyLifeCycle {

    /**
     * 返回值
     *
     * @return
     */
    abstract <T> BaseResult<T> doService();


    @Override
    public BaseResult<T> execute() {
        BaseResult<T> ret;
        try {
            log.info("do service check");
            BaseResult checkResult = check();
            Preconditions.checkNotNull(checkResult, "check result cannot null");
            if (!checkResult.getSuccess()) {
                log.warn("check result is false. [{}]", checkResult);
                return checkResult;
            }
            log.info("do service check=true");


            log.info("do service validate");
            BaseResult validateResult = validate();

            Preconditions.checkNotNull(checkResult, "validate result cannot null");

            if (!validateResult.getSuccess()) {
                log.warn("validate result is false. [{}]", validateResult);

                return validateResult;
            }
            log.info("do service validate=true");


            log.info("do service begin");

            ret = doService();

            log.info("do service end");

        } finally {
            log.info("do service destroy begin");

            destroy();

            log.info("do service destroy end");
        }

        return ret;
    }
}
