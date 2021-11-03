package com.github.spy.sea.core.component.condition.judge;


import com.github.spy.sea.core.component.condition.dto.ConditionData;

/**
 * Predicate judge.
 */
@FunctionalInterface
public interface PredicateJudge {

    /**
     * judge conditionData and realData is match.
     *
     * @param conditionData {@linkplain ConditionData}
     * @param realData      realData
     * @return true is pass  false is not pass.
     */
    Boolean judge(ConditionData conditionData, String realData);
}
