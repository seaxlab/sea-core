package com.github.spy.sea.core.function.limit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/3
 * @since 1.0
 */
public interface RateLimit {

    /**
     * 获取资源
     *
     * @return
     */
    boolean tryRequire();

    /**
     * 重置计数
     */
    void reset();

    /**
     * 销毁
     */
    void destroy();
}
