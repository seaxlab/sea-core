package com.github.seaxlab.core.component.service.lifecycle;

import com.github.seaxlab.core.model.Result;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
public interface AbstractExecuteLifeCycle<T> {


    /**
     * 可执行的服务
     *
     * @param <T>
     * @return
     */
    <T> Result<T> execute();
}
