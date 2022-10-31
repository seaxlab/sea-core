package com.github.seaxlab.core.example2.feign;

import com.github.seaxlab.core.example2.feign.response.UserRespDTO;
import com.github.seaxlab.core.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@FeignClient(name = "user-service", path = "/api/user", fallback = UserApiFallback.class)
public interface UserApi {

    @GetMapping("/detail")
    Result<UserRespDTO> getUserInfo(Long userId);

    @PostMapping("/search")
    Result<List<UserRespDTO>> search(Long userId);
}
