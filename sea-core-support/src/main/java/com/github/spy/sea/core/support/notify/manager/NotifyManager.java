package com.github.spy.sea.core.support.notify.manager;

import com.github.spy.sea.core.model.BaseResult;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
public interface NotifyManager {

    /**
     * send message
     *
     * @param msg
     * @return
     */
    void send(String msg);

    /**
     * send msg
     *
     * @param title
     * @param msg
     */
    void send(String title, String msg);

    BaseResult sendAndGet(String msg);

    BaseResult sendAndGet(String title, String msg);
}
