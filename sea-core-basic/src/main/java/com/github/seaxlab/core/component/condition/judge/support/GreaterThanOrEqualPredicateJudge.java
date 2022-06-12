package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Equals predicate judge.
 */
@Slf4j
@LoadLevel(name = "greaterThanOrEqual")
public class GreaterThanOrEqualPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        int input = Integer.parseInt(realData);
        int base = Integer.parseInt(conditionData.getParamValue().trim());

        return input >= base;
    }
}
