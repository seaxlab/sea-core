package com.github.seaxlab.core.component.id;

/**
 * 全局ID接口
 *
 * @author shi.pengyan
 * @version 1.0 2017-07-07 13:19
 * @since 1.0
 */
public interface IdWorker {

    /**
     * 生成全局ID
     *
     * @return
     */
    long next();
}
