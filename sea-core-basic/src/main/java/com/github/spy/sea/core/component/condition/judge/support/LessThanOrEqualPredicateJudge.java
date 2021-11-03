package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Equals predicate judge.
 */
@Slf4j
@LoadLevel(name = "lessThanOrEqual")
public class LessThanOrEqualPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        int input = Integer.parseInt(realData);
        int base = Integer.parseInt(conditionData.getParamValue().trim());

        return input <= base;
    }
}
