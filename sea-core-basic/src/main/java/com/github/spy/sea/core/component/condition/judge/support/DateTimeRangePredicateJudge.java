package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.AbstractPredicateJudge;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.enums.DateFormatEnum;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer after predicate judge.
 */
@Slf4j
@LoadLevel(name = "dateTimeRange")
public class DateTimeRangePredicateJudge extends AbstractPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        String[] arrays = parseArray2(conditionData.getParamValue());

        SimpleDateFormat sdf = DateUtil.getSdf(DateFormatEnum.yyyy_MM_dd_HH_mm_ss);

        try {
            Date input = sdf.parse(realData);
            Date begin = sdf.parse(arrays[0]);
            Date end = sdf.parse(arrays[1]);

            return begin.getTime() <= input.getTime() && input.getTime() <= end.getTime();
        } catch (Exception e) {
            log.error("fail to sdf parse", e);
        }
        return false;
    }
}
