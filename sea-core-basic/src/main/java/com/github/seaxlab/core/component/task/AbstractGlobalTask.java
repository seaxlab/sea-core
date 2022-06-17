package com.github.seaxlab.core.component.task;

import com.github.seaxlab.core.common.Env;
import lombok.extern.slf4j.Slf4j;

/**
 * abstract task for all global.
 *
 * @author spy
 * @version 1.0 2020/11/10
 * @since 1.0
 */
@Slf4j
public abstract class AbstractGlobalTask {

    /**
     * 本机模式下默认不执行TASK
     *
     * @return
     */
    protected boolean checkRunFlag() {
        if (Env.isLocalMode()) {
            return false;
        }
        return true;
    }
}
