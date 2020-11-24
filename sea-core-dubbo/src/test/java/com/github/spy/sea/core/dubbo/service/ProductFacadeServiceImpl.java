package com.github.spy.sea.core.dubbo.service;

import com.github.spy.sea.core.dubbo.facade.ProductFacadeService;
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
public class ProductFacadeServiceImpl implements ProductFacadeService {

    private AtomicLong count = new AtomicLong(0);

    @Override
    public String get(Long productId) {
        log.info("productId={},count={}", productId, count.incrementAndGet());

        return "product" + count.get();
    }
}
