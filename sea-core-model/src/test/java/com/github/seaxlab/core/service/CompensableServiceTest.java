package com.github.seaxlab.core.service;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.model.layer.dto.DTO;
import com.github.seaxlab.core.model.service.CompensableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/7
 * @since 1.0
 */
@Slf4j
public class CompensableServiceTest {

    @Test
    public void test16() throws Exception {
        CompensableService cs = new CompensableService<DTO, Boolean>() {
            @Override
            public Result<Boolean> compensate(DTO o) {
                return null;
            }
        };

    }
}
