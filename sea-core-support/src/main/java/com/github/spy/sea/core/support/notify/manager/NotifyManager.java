package com.github.spy.sea.core.support.notify.manager;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.notify.dto.BaseNotifyDTO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
public interface NotifyManager<T extends BaseNotifyDTO> {

    /**
     * send message
     *
     * @param msg
     * @return
     */
    @Deprecated
    void send(String msg);

    /**
     * send msg
     *
     * @param title
     * @param msg
     */
    @Deprecated
    void send(String title, String msg);

    /**
     * 发送消息
     *
     * @param dto
     * @return
     */
    BaseResult send(T dto);
}
