package com.github.spy.sea.core.component.tracer.sofa.simple;

import com.alipay.common.tracer.core.appender.builder.XStringBuilder;
import com.alipay.common.tracer.core.appender.self.Timestamp;
import com.alipay.common.tracer.core.constants.SofaTracerConstant;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.middleware.parent.AbstractDigestSpanEncoder;
import com.alipay.common.tracer.core.span.CommonSpanTags;
import com.alipay.common.tracer.core.span.SofaTracerSpan;

import java.io.IOException;
import java.util.Map;

/**
 * 摘要文件
 *
 * @author spy
 * @version 1.0 2019-07-09
 * @since 1.0
 */
public class SimpleDigestEncoder extends AbstractDigestSpanEncoder {

    @Override
    public String encode(SofaTracerSpan span) throws IOException {

        XStringBuilder xsb = new XStringBuilder();
        xsb.reset();
        //time
        xsb.append(Timestamp.format(span.getEndTime()));
        appendSlot(xsb, span);
        return xsb.toString();
    }

    private void appendSlot(XStringBuilder xsb, SofaTracerSpan sofaTracerSpan) {

        SofaTracerSpanContext context = sofaTracerSpan.getSofaTracerSpanContext();
        Map<String, String> tagWithStr = sofaTracerSpan.getTagsWithStr();
        Map<String, Number> tagWithNumber = sofaTracerSpan.getTagsWithNumber();
        //appName
        xsb.append(tagWithStr.get(CommonSpanTags.LOCAL_APP));
        //TraceId
        xsb.append(context.getTraceId());
        //RpcId
        xsb.append(context.getSpanId());
        //URL
        xsb.append(tagWithStr.get(CommonSpanTags.REQUEST_URL));
        //method
        xsb.append(tagWithStr.get(CommonSpanTags.METHOD));
        //Http code
        xsb.append(tagWithStr.get(CommonSpanTags.RESULT_CODE));
        Number requestSize = tagWithNumber.get(CommonSpanTags.REQ_SIZE);
        //Request Body bytes
        xsb.append((requestSize == null ? 0L : requestSize.longValue()) + SofaTracerConstant.BYTE);
        Number responseSize = tagWithNumber.get(CommonSpanTags.RESP_SIZE);
        //Response Body bytes
        xsb.append((responseSize == null ? 0L : responseSize.longValue()) + SofaTracerConstant.BYTE);
        //cost
        xsb.append((sofaTracerSpan.getEndTime() - sofaTracerSpan.getStartTime()) + SofaTracerConstant.MS);
        xsb.append(tagWithStr.get(CommonSpanTags.CURRENT_THREAD_NAME));
        //sys baggage
        xsb.append(context.getSysBaggage());
        //baggage
        xsb.appendEnd(context.getBizBaggage());
    }

}