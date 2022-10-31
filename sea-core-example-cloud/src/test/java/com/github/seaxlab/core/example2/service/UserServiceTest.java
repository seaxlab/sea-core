package com.github.seaxlab.core.example2.service;

import com.github.seaxlab.core.example2.BaseSpringTest;
import com.github.seaxlab.core.example2.feign.UserApi;
import com.github.seaxlab.core.example2.feign.response.UserRespDTO;
import com.github.seaxlab.core.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * User Service test
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class UserServiceTest extends BaseSpringTest {

    private final UserApi userApi;

    @Test
    public void testGetUserInfo() throws Exception {
        Result<UserRespDTO> result = userApi.getUserInfo(1L);
        log.info("User={}", result);
    }

    @Test
    public void testSearch() throws Exception {
        Result<List<UserRespDTO>> result = userApi.search(1L);
        log.info("Users={}", result);
    }
}
