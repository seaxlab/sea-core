package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer after predicate judge.
 */
@Slf4j
@LoadLevel(name = "dateBefore")
public class DateBeforePredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DAY_FORMAT);

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
