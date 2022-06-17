package com.github.seaxlab.core.component.lifecycle;

import com.github.seaxlab.core.exception.BaseAppException;

/**
 * life cycle component
 *
 * @author spy
 * @version 1.0
 */
public interface Lifecycle {

    /**
     * 正常启动
     *
     * @throws BaseAppException
     */
    void init() throws BaseAppException;

    /**
     * 正常停止
     *
     * @throws BaseAppException
     */
    void destroy() throws BaseAppException;

    /**
     * 是否存储运行运行状态
     *
     * @return
     * @throws BaseAppException
     */
    boolean isInited() throws BaseAppException;

}
