package com.github.seaxlab.core.example.web.controller;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/8/31
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/tunnel")
public class TunnelTestController {

  private final TunnelService tunnelService;

  @PostMapping("/execute")
  public Result<?> execute(@RequestBody ExecuteReqBO bo) {
    return tunnelService.execute(bo);
  }

}
