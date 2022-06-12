package com.github.seaxlab.core.component.tracer.sofa.simple;

import com.alipay.common.tracer.core.appender.builder.JsonStringBuilder;
import com.alipay.common.tracer.core.appender.file.LoadTestAwareAppender;
import com.alipay.common.tracer.core.appender.self.SelfLog;
import com.alipay.common.tracer.core.appender.self.Timestamp;
import com.alipay.common.tracer.core.reporter.stat.model.StatKey;
import com.alipay.common.tracer.core.reporter.stat.model.StatMapKey;
import com.alipay.common.tracer.core.span.CommonSpanTags;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.common.tracer.core.utils.TracerUtils;

import java.util.Map;

/**
 * 统计文件json格式
 *
 * @author spy
 * @version 1.0 2019-07-09
 * @since 1.0
 */
public class SimpleStatJsonReporter extends SimpleStatReporter {

    /***
     * 输出拼接器
     */
    private static JsonStringBuilder jsonBuffer = new JsonStringBuilder();

    public SimpleStatJsonReporter(String statTracerName, String rollingPolicy,
                                  String logReserveConfig) {
        super(statTracerName, rollingPolicy, logReserveConfig);
    }

    @Override
    public void doReportStat(SofaTracerSpan sofaTracerSpan) {
        Map<String, String> tagsWithStr = sofaTracerSpan.getTagsWithStr();
        StatMapKey statKey = new StatMapKey();
        statKey.addKey(CommonSpanTags.LOCAL_APP, tagsWithStr.get(CommonSpanTags.LOCAL_APP));
        statKey.addKey(CommonSpanTags.REQUEST_URL, tagsWithStr.get(CommonSpanTags.REQUEST_URL));
        statKey.addKey(CommonSpanTags.METHOD, tagsWithStr.get(CommonSpanTags.METHOD));
        //pressure mark
        statKey.setLoadTest(TracerUtils.isLoadTest(sofaTracerSpan));
        //success
        String resultCode = tagsWithStr.get(CommonSpanTags.RESULT_CODE);
        boolean success = (resultCode != null && resultCode.length() > 0 && this
                .isHttpOrMvcSuccess(resultCode));
        statKey.setResult(success ? "true" : "false");
        //end
        statKey.setEnd(TracerUtils.getLoadTestMark(sofaTracerSpan));
        //value
        //次数和耗时，最后一个耗时是单独打印的字段
        long duration = sofaTracerSpan.getEndTime() - sofaTracerSpan.getStartTime();
        long values[] = new long[]{1, duration};
        //reserve
        this.addStat(statKey, values);
    }

    @Override
    public void print(StatKey statKey, long[] values) {
        if (this.isClosePrint.get()) {
            //关闭统计日志输出
            return;
        }
        if (!(statKey instanceof StatMapKey)) {
            return;
        }
        StatMapKey statMapKey = (StatMapKey) statKey;
        try {
            jsonBuffer.reset();
            jsonBuffer.appendBegin();
            jsonBuffer.append("time", Timestamp.currentTime());
            jsonBuffer.append("stat.key", this.statKeySplit(statMapKey));
            jsonBuffer.append("count", values[0]);
            jsonBuffer.append("total.cost.milliseconds", values[1]);
            jsonBuffer.append("success", statMapKey.getResult());
            //压测
            jsonBuffer.appendEnd("load.test", statMapKey.getEnd());

            if (appender instanceof LoadTestAwareAppender) {
                ((LoadTestAwareAppender) appender).append(jsonBuffer.toString(),
                        statMapKey.isLoadTest());
            } else {
                appender.append(jsonBuffer.toString());
            }
            // 这里强制刷一次
            appender.flush();
        } catch (Throwable t) {
            SelfLog.error("统计日志<" + statTracerName + ">输出异常", t);
        }
    }

    private String statKeySplit(StatMapKey statKey) {
        JsonStringBuilder jsonBufferKey = new JsonStringBuilder();
        Map<String, String> keyMap = statKey.getKeyMap();
        jsonBufferKey.appendBegin();
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            jsonBufferKey.append(entry.getKey(), entry.getValue());
        }
        jsonBufferKey.appendEnd(false);
        return jsonBufferKey.toString();
    }
}