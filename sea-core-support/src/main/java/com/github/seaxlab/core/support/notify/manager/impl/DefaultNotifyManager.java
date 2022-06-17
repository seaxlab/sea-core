package com.github.seaxlab.core.support.notify.manager.impl;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.notify.dto.BaseNotifyDTO;
import com.github.seaxlab.core.support.notify.manager.NotifyManager;
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
    public Result send(BaseNotifyDTO dto) {
        log.info("[default notify manager] dto={}", dto);

        return Result.success();
    }
}
