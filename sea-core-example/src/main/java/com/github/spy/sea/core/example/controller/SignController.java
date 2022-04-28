package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.example.controller.dto.UserSaveDTO;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/4/28
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/sign")
public class SignController {

    @PostMapping("/test")
    public Result test(@RequestBody UserSaveDTO dto) {
        log.info("dto={}", dto);

        return Result.success();
    }

}
