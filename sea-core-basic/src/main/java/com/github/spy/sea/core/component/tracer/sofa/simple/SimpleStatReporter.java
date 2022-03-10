package com.github.spy.sea.core.component.tracer.sofa.simple;

import com.alipay.common.tracer.core.reporter.stat.AbstractSofaTracerStatisticReporter;
import com.alipay.common.tracer.core.reporter.stat.model.StatKey;
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
public class SimpleStatReporter extends AbstractSofaTracerStatisticReporter {

    public SimpleStatReporter(String statTracerName, String rollingPolicy, String logReserveConfig) {
        super(statTracerName, rollingPolicy, logReserveConfig);
    }

    @Override
    public void doReportStat(SofaTracerSpan sofaTracerSpan) {
        Map<String, String> tagsWithStr = sofaTracerSpan.getTagsWithStr();
        StatKey statKey = new StatKey();
        statKey
                .setKey(buildString(new String[]{tagsWithStr.get(CommonSpanTags.LOCAL_APP),
                        tagsWithStr.get(CommonSpanTags.REQUEST_URL),
                        tagsWithStr.get(CommonSpanTags.METHOD)}));
        String resultCode = tagsWithStr.get(CommonSpanTags.RESULT_CODE);
        boolean success = (resultCode != null && resultCode.length() > 0 && this
                .isHttpOrMvcSuccess(resultCode));
        statKey.setResult(success ? "Y" : "N");
        statKey.setEnd(buildString(new String[]{TracerUtils.getLoadTestMark(sofaTracerSpan)}));
        //pressure mark
        statKey.setLoadTest(TracerUtils.isLoadTest(sofaTracerSpan));

        //次数和耗时，最后一个耗时是单独打印的字段
        long duration = sofaTracerSpan.getEndTime() - sofaTracerSpan.getStartTime();
        long values[] = new long[]{1, duration};
        this.addStat(statKey, values);
    }
}