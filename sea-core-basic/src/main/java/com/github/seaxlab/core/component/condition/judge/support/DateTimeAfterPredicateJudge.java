package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.loader.LoadLevel;
import com.github.seaxlab.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/**
 * Timer after predicate judge.
 */
@Slf4j
@LoadLevel(name = "dateTimeAfter")
public class DateTimeAfterPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        String paramName = conditionData.getParamName();
        Date paramDateTime = DateUtil.toDate(conditionData.getParamValue(), DateFormatEnum.yyyy_MM_dd_HH_mm_ss);

        if (Objects.isNull(paramName)) {
            return DateUtil.nowDate().after(paramDateTime);
        }
        Date realDateTime = DateUtil.toDate(realData, DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
        return realDateTime.after(paramDateTime);
    }
}
