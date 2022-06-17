package com.github.seaxlab.core.support.notify.manager;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.notify.dto.BaseNotifyDTO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
public interface NotifyManager<T extends BaseNotifyDTO> {

    /**
     * 发送消息
     *
     * @param dto
     * @return
     */
    Result send(T dto);
}
