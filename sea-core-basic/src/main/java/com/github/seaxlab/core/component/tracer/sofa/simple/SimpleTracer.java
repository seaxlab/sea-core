package com.github.seaxlab.core.component.tracer.sofa.simple;

import com.alipay.common.tracer.core.appender.encoder.SpanEncoder;
import com.alipay.common.tracer.core.configuration.SofaTracerConfiguration;
import com.alipay.common.tracer.core.context.trace.SofaTraceContext;
import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.alipay.common.tracer.core.reporter.stat.AbstractSofaTracerStatisticReporter;
import com.alipay.common.tracer.core.span.CommonSpanTags;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.common.tracer.core.tracer.AbstractClientTracer;
import com.github.seaxlab.core.util.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 简单跟踪器 适用场景：非web入口，手动添加入口
 *
 * @author spy
 * @version 1.0 2019-07-08
 * @since 1.0
 */
@Slf4j
public class SimpleTracer extends AbstractClientTracer {

  public static final String SIMPLE_TRACER_JSON_FORMAT_OUTPUT = "simple_tracer_json_format_output";


  public static final String RESULT_CODE_SUC = "success";

  public static final String RESULT_CODE_FAIL = "fail";

  public static final String SEA_SOFA_TRACER = "sea.sofa.simple.tracer";
  public static final String SEA_SOFA_TRACER_MODE_CUSTOM = "custom";

  public static final String SEA_SOFA_TRACER_ERROR_MSG = "sea.sofa.simple.tracer.error.msg";


  private volatile static SimpleTracer tracer = null;

  /**
   * Simple Tracer Singleton
   *
   * @return singleton
   */
  public static SimpleTracer getTracerSingleton() {
    if (tracer == null) {
      synchronized (SimpleTracer.class) {
        if (tracer == null) {
          tracer = new SimpleTracer();
        }
      }
    }
    return tracer;
  }


  public SimpleTracer() {
    super("SimpleTracer");
  }

  @Override
  protected String getClientDigestReporterLogName() {
    return SimpleLogEnum.DIGEST.getDefaultLogName();
  }

  @Override
  protected String getClientDigestReporterRollingKey() {
    return SimpleLogEnum.DIGEST.getRollingKey();
  }

  @Override
  protected String getClientDigestReporterLogNameKey() {
    return SimpleLogEnum.DIGEST.getDefaultLogName();
  }

  @Override
  protected SpanEncoder<SofaTracerSpan> getClientDigestEncoder() {
    if (Boolean.TRUE.toString().equalsIgnoreCase(
      SofaTracerConfiguration.getProperty(SIMPLE_TRACER_JSON_FORMAT_OUTPUT))) {
      return new SimpleDigestJsonEncoder();
    } else {
      return new SimpleDigestEncoder();
    }
  }

  @Override
  protected AbstractSofaTracerStatisticReporter generateClientStatReporter() {
    return generateSofaSimpleStatReporter();
  }


  private SimpleStatReporter generateSofaSimpleStatReporter() {
    SimpleLogEnum springMvcLogEnum = SimpleLogEnum.STAT;
    String statLog = springMvcLogEnum.getDefaultLogName();
    String statRollingPolicy = SofaTracerConfiguration.getRollingPolicy(springMvcLogEnum
      .getRollingKey());
    String statLogReserveConfig = SofaTracerConfiguration.getLogReserveConfig(springMvcLogEnum
      .getLogNameKey());
    if (Boolean.TRUE.toString().equalsIgnoreCase(
      SofaTracerConfiguration.getProperty(SIMPLE_TRACER_JSON_FORMAT_OUTPUT))) {
      return new SimpleStatJsonReporter(statLog, statRollingPolicy, statLogReserveConfig);
    } else {
      return new SimpleStatReporter(statLog, statRollingPolicy, statLogReserveConfig);
    }
  }

  /**
   * 开启Trace
   */
  public SofaTracerSpan begin(String operationName) {
    SofaTracerSpan span = clientSend(operationName);

    String appName = SofaTracerConfiguration
      .getProperty(SofaTracerConfiguration.TRACER_APPNAME_KEY);

//        if (log.isDebugEnabled()) {
//            log.debug("appName={}", appName);
//        }

    span.setTag(CommonSpanTags.LOCAL_APP, appName);
    span.setTag(SEA_SOFA_TRACER, SEA_SOFA_TRACER_MODE_CUSTOM);

    return span;
  }

  /**
   * set format message.
   *
   * @param messagePattern
   * @param args
   * @return
   */
  public SofaTracerSpan beginF(String messagePattern, Object... args) {
    return begin(TemplateUtil.format(messagePattern, args));
  }

  /**
   * add tag
   *
   * @param key
   * @param value
   */
  public void tag(String key, String value) {
    SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
    if (sofaTraceContext != null) {
      SofaTracerSpan span = sofaTraceContext.getCurrentSpan();
      if (span != null) {
        span.setTag(key, value);
      }
    }
  }

  /**
   * set format message
   *
   * @param key
   * @param messagePattern
   * @param args
   */
  public void tagF(String key, String messagePattern, Object... args) {
    tag(key, TemplateUtil.format(messagePattern, args));
  }


  /**
   * set error msg
   *
   * @param errorMsg
   */
  public void setErrorMsg(String errorMsg) {
    SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
    if (sofaTraceContext != null) {
      SofaTracerSpan span = sofaTraceContext.getCurrentSpan();
      if (span != null) {
        span.setTag(SEA_SOFA_TRACER_ERROR_MSG, errorMsg);
      }
    }
  }

  /**
   * format message.
   *
   * @param messagePattern
   * @param args
   */
  public void setErrorMsgF(String messagePattern, Object... args) {
    setErrorMsg(TemplateUtil.format(messagePattern, args));
  }


  /**
   * set exception.
   *
   * @param t
   */
  public void setException(Throwable t) {
    if (t == null) {
      return;
    }
    setErrorMsg(ExceptionUtils.getMessage(t));
  }

  /**
   * 结束Trace
   *
   * @param resultCode RESULT_CODE_SUC/RESULT_CODE_FAIL
   */
  public void end(String resultCode) {
    if (RESULT_CODE_FAIL.equalsIgnoreCase(resultCode)) {
      SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
      if (sofaTraceContext != null) {
        SofaTracerSpan span = sofaTraceContext.getCurrentSpan();
        if (span != null) {
          span.setTag("error", true);
        }
      }
    }
    try {
      clientReceive(resultCode);
    } catch (Exception e) {
      log.error("fail to execute client receive", e);
    }
  }

  /**
   * reset by force.
   */
  public void reset() {
    SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
    sofaTraceContext.clear();
  }

}
