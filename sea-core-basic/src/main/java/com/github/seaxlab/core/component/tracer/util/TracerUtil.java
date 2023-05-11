package com.github.seaxlab.core.component.tracer.util;

import com.github.seaxlab.core.component.tracer.enums.TracerProviderEnum;
import com.github.seaxlab.core.component.tracer.skywalking.util.SkyWalkingUtil;
import com.github.seaxlab.core.component.tracer.sofa.util.SofaTracerUtil;
import com.github.seaxlab.core.config.ConfigurationFactory;
import com.github.seaxlab.core.enums.ConfigKeyEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * tracer util
 *
 * @author spy
 * @version 1.0 2022/08/31
 * @since 1.0
 */
@Slf4j
public class TracerUtil {

  private TracerUtil() {
  }

  /**
   * get trace id
   *
   * @return string
   */
  public static String getTraceId() {
    String mode = ConfigurationFactory.getInstance().getString(ConfigKeyEnum.TRACE_PROVIDER.getCode(), "none");

    if (EqualUtil.isEq(mode, TracerProviderEnum.SKYWALKING.getCode(), false)) {
      return SkyWalkingUtil.getTraceId();
    }
    if (EqualUtil.isEq(mode, TracerProviderEnum.SOFA.getCode(), false)) {
      return SofaTracerUtil.getTraceId();
    }
    log.warn("tracer provider enum is not config!");
    //
    return "NONE";
  }

  //----------------------------------------


}
