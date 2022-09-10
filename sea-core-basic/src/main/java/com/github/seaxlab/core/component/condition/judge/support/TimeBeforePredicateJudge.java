package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer before predicate judge.
 */
@Slf4j
@LoadLevel(name = "timeBefore")
public class TimeBeforePredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.HH_mm_ss.getValue());

        try {
            Date input = sdf.parse(realData);
            Date base = sdf.parse(conditionData.getParamValue());

            return input.before(base);
        } catch (Exception e) {
            log.error("fail to sdf parse", e);
        }
        return false;
    }
}
