package com.github.seaxlab.core.spring.aop.config;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/23
 * @since 1.0
 */
@Slf4j
public class AopGlobalConfig {

  // 判断是否允许打印耗时日志
  private static boolean logCostFlag = true;
  // 判断是否允许打印请求日志
  private static boolean logRequestFlag = true;
  // 是否打印bean public method
  private static boolean logPublicMethodFlag = true;

  public static boolean getLogCostFlag() {
    return logCostFlag;
  }

  public static void setLogCostFlag(boolean enable) {
    logCostFlag = enable;
  }

  public static boolean getLogRequestFlag() {
    return logRequestFlag;
  }

  public static void setLogRequestFlag(boolean flag) {
    logRequestFlag = flag;
  }

  public static boolean getLogPublicMethodFlag() {
    return logPublicMethodFlag;
  }

  public static void setLogPublicMethodFlag(boolean flag) {
    logPublicMethodFlag = flag;
  }

}
