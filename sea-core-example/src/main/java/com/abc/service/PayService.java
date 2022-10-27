package com.abc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * pay service
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PayService {

    public int add(int a, int b) {
        log.info("pay add.");
        return a + b;
    }

    public void test() {
        log.info("pay test execute.");
    }

}
