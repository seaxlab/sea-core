package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.example.controller.dto.Param1DTO;
import com.github.spy.sea.core.example.controller.dto.Param2DTO;
import com.github.spy.sea.core.example.controller.dto.Param3DTO;
import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.spring.component.json.annotation.JsonParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/param")
public class MultiParamController {

    /**
     * 对RequestBody绑定到多个参数
     *
     * @param dto1
     * @param dto2
     * @param dto3
     * @return
     */
    @PostMapping("/multi")
    public Result test(@JsonParam Param1DTO dto1, @JsonParam Param2DTO dto2, @JsonParam(path = "dto3") Param3DTO dto3) {

        log.info("dto1={}", dto1);
        log.info("dto2={}", dto2);
        log.info("dto3={}", dto3);

        return Result.success();
    }

}
