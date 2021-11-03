package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/**
 * Timer after predicate judge.
 */
@Slf4j
@LoadLevel(name = "dateTimeBefore")
public class DateTimeBeforePredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        String paramName = conditionData.getParamName();
        Date paramDateTime = DateUtil.str2Date(conditionData.getParamValue(), DateUtil.DEFAULT_FORMAT);

        if (Objects.isNull(paramName)) {
            return DateUtil.nowDate().after(paramDateTime);
        }
        Date realDateTime = DateUtil.str2Date(realData, DateUtil.DEFAULT_FORMAT);
        return realDateTime.after(paramDateTime);
    }
}
