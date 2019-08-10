package com.github.spy.sea.core.support.notify.manager.impl;

import com.github.spy.sea.core.support.notify.manager.NotifyManager;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
@Slf4j
public class DefaultNotifyManager implements NotifyManager {
    @Override
    public void send(String msg) {
        log.warn("msg={}", msg);
    }

    @Override
    public void send(String title, String msg) {
        log.warn("title={},msg={}", title, msg);
    }
}
