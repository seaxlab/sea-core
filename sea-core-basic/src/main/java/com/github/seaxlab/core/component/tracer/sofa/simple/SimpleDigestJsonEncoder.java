package com.github.seaxlab.core.component.tracer.sofa.simple;

import com.alipay.common.tracer.core.appender.builder.JsonStringBuilder;
import com.alipay.common.tracer.core.appender.self.Timestamp;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.middleware.parent.AbstractDigestSpanEncoder;
import com.alipay.common.tracer.core.span.CommonSpanTags;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.common.tracer.core.utils.StringUtils;
import io.opentracing.tag.Tags;
import java.io.IOException;
import java.util.Map;

/**
 * 摘要文件json格式
 *
 * @author spy
 * @version 1.0 2019-07-09
 * @since 1.0
 */
public class SimpleDigestJsonEncoder extends AbstractDigestSpanEncoder {

  @Override
  public String encode(SofaTracerSpan span) throws IOException {
    JsonStringBuilder jsonStringBuilder = new JsonStringBuilder();
    //日志打印时间
    jsonStringBuilder.appendBegin("time", Timestamp.format(span.getEndTime()));
    appendSlot(jsonStringBuilder, span);
    return jsonStringBuilder.toString();
  }

  private void appendSlot(JsonStringBuilder jsonStringBuilder, SofaTracerSpan sofaTracerSpan) {

    SofaTracerSpanContext context = sofaTracerSpan.getSofaTracerSpanContext();
    Map<String, String> tagWithStr = sofaTracerSpan.getTagsWithStr();
    Map<String, Number> tagWithNumber = sofaTracerSpan.getTagsWithNumber();
    //当前应用名
    jsonStringBuilder
      .append(CommonSpanTags.LOCAL_APP, tagWithStr.get(CommonSpanTags.LOCAL_APP));
    //remote address, webflux
    if (tagWithStr.get(CommonSpanTags.REMOTE_APP) != null) {
      jsonStringBuilder.append(CommonSpanTags.REMOTE_APP,
        tagWithStr.get(CommonSpanTags.REMOTE_APP));
    }
    //TraceId
    jsonStringBuilder.append("traceId", context.getTraceId());
    //RpcId
    jsonStringBuilder.append("spanId", context.getSpanId());
    //请求 URL
    jsonStringBuilder.append(CommonSpanTags.REQUEST_URL,
      tagWithStr.get(CommonSpanTags.REQUEST_URL));
    //请求方法
    jsonStringBuilder.append(CommonSpanTags.METHOD, tagWithStr.get(CommonSpanTags.METHOD));

    //Http 状态码
    jsonStringBuilder.append(CommonSpanTags.RESULT_CODE,
      tagWithStr.get(CommonSpanTags.RESULT_CODE));
    //异常信息
    if (StringUtils.isNotBlank(tagWithStr.get(Tags.ERROR.getKey()))) {
      jsonStringBuilder.append(Tags.ERROR.getKey(), tagWithStr.get(Tags.ERROR.getKey()));
    }
    //Http 状态码
    jsonStringBuilder.append(CommonSpanTags.RESULT_CODE,
      tagWithStr.get(CommonSpanTags.RESULT_CODE));
    Number requestSize = tagWithNumber.get(CommonSpanTags.REQ_SIZE);
    //Request Body 大小 单位为byte
    jsonStringBuilder.append(CommonSpanTags.REQ_SIZE,
      (requestSize == null ? 0L : requestSize.longValue()));
    Number responseSize = tagWithNumber.get(CommonSpanTags.RESP_SIZE);
    //Response Body 大小，单位为byte
    jsonStringBuilder.append(CommonSpanTags.RESP_SIZE, (responseSize == null ? 0L
      : responseSize.longValue()));
    //请求耗时（MS）
    jsonStringBuilder.append("time.cost.milliseconds",
      (sofaTracerSpan.getEndTime() - sofaTracerSpan.getStartTime()));
    jsonStringBuilder.append(CommonSpanTags.CURRENT_THREAD_NAME,
      tagWithStr.get(CommonSpanTags.CURRENT_THREAD_NAME));
    //穿透数据放在最后
    jsonStringBuilder.appendEnd("baggage", baggageSerialized(context));
  }
}