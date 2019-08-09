package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/log/get")
    public BaseResult getTest() {
        return BaseResult.success();
    }

    @PostMapping("/log/post")
    public BaseResult postTest(HttpServletRequest request) {

        return BaseResult.success();
    }

    @PostMapping("/log/post_json")
    public BaseResult postJSONTest(HttpServletRequest request) {


        log.info("my request body={}", RequestUtil.getRequestBody(request));
        return BaseResult.success();
    }


}
