package com.github.seaxlab.core.example.web.controller;

import com.abc.service.PayService;
import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.annotation.LogCost;
import com.github.seaxlab.core.spring.annotation.LogRequest;
import com.github.seaxlab.core.thread.util.CallableUtil;
import com.github.seaxlab.core.thread.util.ThreadPoolUtil;
import com.github.seaxlab.core.thread.util.ThreadUtil;
import com.github.seaxlab.core.web.util.RequestUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TestController implements InitializingBean {

  private final PayService payService;
  private final LockService lockService;

  @GetMapping("/lock")
  public Result lock() {
    lockService.tryLock("test:11", "test", () -> {
      log.info("abc");
      log.info("abc2");
    });

    return Result.success();
  }

  @LogCost
  @LogRequest
  @GetMapping("/log")
  public Result log() {
    log.info("----log");

    payService.add(1, 2);
    payService.test();

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
