package com.github.seaxlab.core.spring.component.tunnel.controller;

import com.github.seaxlab.core.spring.component.tunnel.bo.BeanEventReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.HttpTunnelReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.StaticTunnelReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.HttpTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.StaticTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.Supplier;

/**
 * tunnel service
 *
 * @author spy
 * @version 1.0 2025/3/24
 * @since 1.0
 */
@Slf4j
@RestController("frmTunnelController250912")
@RequestMapping("/api/test/tunnel")
//@RequiredArgsConstructor
//@Api(value = "Tunnel", hidden = true)
public class TunnelController {

  @Resource
  private TunnelService tunnelService;

  @Resource
  private HttpTunnelService httpTunnelService;

  @Resource
  private StaticTunnelService staticTunnelService;

  /**
   * 执行bean
   *
   * @param bo request
   * @return result
   */
  @PostMapping("/bean")
  public Map<String, Object> execute(@RequestBody ExecuteReqBO bo) {
    return safe(() -> tunnelService.executeSimple(bo));
  }

  /**
   * 执行application event
   *
   * @param bo
   * @return
   */
  @PostMapping("/bean/event")
  public Map<String, Object> executeEvent(@RequestBody BeanEventReqBO bo) {
    return safe(() -> tunnelService.executeEvent(bo));
  }

  /**
   * 执行http
   *
   * @param bo request
   * @return result
   */
  @PostMapping("/http")
  public Map<String, Object> executeHttp(@RequestBody HttpTunnelReqBO bo) {
    return safe(() -> httpTunnelService.execute(bo));
  }

  /**
   * 执行静态类方法
   *
   * @param bo request
   * @return result
   */
  @PostMapping("/static")
  public Map<String, Object> executeStatic(@RequestBody StaticTunnelReqBO bo) {
    return safe(() -> staticTunnelService.execute(bo));
  }

  //----------------------------------------------------------------
  private Map<String, Object> safe(Supplier<?> supplier) {
    Object value = null;
    try {
      value = supplier.get();
      // if value is result type, just skip it, it is not standard value
    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      //
      return createError("500", ExceptionUtils.getRootCauseMessage(e));
    }

    return createSuccess(value);
  }

  //----------------------------------------------------------------

  private Map<String, Object> createSuccess(Object data) {
    return Map.of("code", 200, "data", data);
  }

  public Map<String, Object> createError(String code, String message) {
    return Map.of("code", code, "message", message);
  }


}
