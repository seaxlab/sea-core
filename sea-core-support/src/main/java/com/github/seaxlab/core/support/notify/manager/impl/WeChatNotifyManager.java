package com.github.seaxlab.core.support.notify.manager.impl;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.notify.dto.WeChatNotifyDTO;
import com.github.seaxlab.core.support.notify.manager.NotifyManager;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/21
 * @since 1.0
 */
@Slf4j
public class WeChatNotifyManager implements NotifyManager<WeChatNotifyDTO> {

    @Override
    public Result send(WeChatNotifyDTO dto) {
        return null;
    }
}
