package com.github.spy.sea.core.service.lifecycle;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
public interface AbstractDestroyLifeCycle {

    /**
     * release resource
     */
    void destroy();
}
