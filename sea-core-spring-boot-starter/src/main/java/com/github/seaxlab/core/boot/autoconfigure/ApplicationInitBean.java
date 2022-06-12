package com.github.seaxlab.core.boot.autoconfigure;

/**
 * 应用启动成功后执行
 *
 * @author spy
 * @version 1.0 2019-06-29
 * @since 1.0
 */
public interface ApplicationInitBean {

    /**
     * 初始化
     */
    void init();
}
