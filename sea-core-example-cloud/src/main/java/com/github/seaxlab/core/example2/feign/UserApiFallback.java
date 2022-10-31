package com.github.seaxlab.core.example2.feign;

import com.github.seaxlab.core.example2.feign.response.UserRespDTO;
import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserApiFallback implements UserApi {

    @Override
    public Result<UserRespDTO> getUserInfo(Long userId) {
        log.warn("get user info fallback");
        return Result.fail();
    }

    @Override
    public Result<List<UserRespDTO>> search(Long userId) {
        log.warn("search fallback");
        return Result.fail();
    }
}