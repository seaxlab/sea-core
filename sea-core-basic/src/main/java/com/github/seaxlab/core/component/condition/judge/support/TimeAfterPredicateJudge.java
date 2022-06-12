package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import com.github.seaxlab.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer after predicate judge.
 */
@Slf4j
@LoadLevel(name = "timeAfter")
public class TimeAfterPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.TIME_FORMAT);

        try {
            Date input = sdf.parse(realData);
            Date base = sdf.parse(conditionData.getParamValue());

            return input.after(base);
        } catch (Exception e) {
            log.error("fail to sdf parse", e);
        }
        return false;
    }
}
