package com.github.spy.sea.core.dubbo.manager;

import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.dubbo.common.dto.AppConfig;
import com.github.spy.sea.core.dubbo.common.dto.DubboGenericInvokeDTO;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
@Beta
public class DubboRemoteManager {

    private AppConfig appConfig;

    private DubboRemoteManager() {
    }

    public DubboRemoteManager(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public Result invoke(DubboGenericInvokeDTO dto) {
        if (appConfig == null) {
            throw new IllegalArgumentException("app config cannot be null.");
        }
        BeanUtils.copyProperties(appConfig, dto);
        return DubboUtil.invoke(dto);
    }
}
