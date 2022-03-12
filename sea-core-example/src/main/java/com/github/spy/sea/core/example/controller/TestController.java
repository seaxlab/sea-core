package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.spring.annotation.LogCost;
import com.github.spy.sea.core.thread.util.CallableUtil;
import com.github.spy.sea.core.thread.util.ThreadPoolUtil;
import com.github.spy.sea.core.thread.util.ThreadUtil;
import com.github.spy.sea.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

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
public class TestController implements InitializingBean {

    @LogCost
    @GetMapping("/log")
    public Result log() {
        log.info("----log");

        return Result.success();
    }

    @GetMapping("/log/get")
    public Result getTest() {
        ThreadPoolExecutor tpe = ThreadPoolUtil.createTemp("sea-test", 4, 4);

        log.info("-----");
        Callable<String> callable = CallableUtil.create(true, () -> {
            log.info("get data");
            return "";
        });
        tpe.submit(callable);
        ThreadUtil.sleepSecond(10);

        return Result.success();
    }

    @PostMapping("/log/post")
    public Result postTest(HttpServletRequest request) {

        return Result.success();
    }

    @PostMapping("/log/post_json")
    public Result postJSONTest(HttpServletRequest request) {


        log.info("my request body={}", RequestUtil.getRequestBody(request));
        return Result.success();
    }

    @Value("sea.list")
    private String[] list;

    @Value("${sea.list2}")
    private String[] list2;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("list={}", list);
        log.info("list2={}", list2);
    }
}
