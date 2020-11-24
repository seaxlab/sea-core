package com.github.spy.sea.core.dubbo.service;

import com.github.spy.sea.core.dubbo.facade.UserFacadeService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/24
 * @since 1.0
 */
@Slf4j
public class UserFacadeServiceImpl implements UserFacadeService {
    private static AtomicLong count = new AtomicLong(0);

    @Override
    public String getName(String userCode) {
        log.info("userCode={},invokeCount={}", userCode, count.incrementAndGet());
        return "name" + count.get();
    }
}
