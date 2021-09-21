package com.github.spy.sea.core.support.notify.manager.impl;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.notify.dto.WeChatNotifyDTO;
import com.github.spy.sea.core.support.notify.manager.NotifyManager;
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
    public void send(String msg) {

    }

    @Override
    public void send(String title, String msg) {

    }

    @Override
    public BaseResult send(WeChatNotifyDTO dto) {
        return null;
    }
}
