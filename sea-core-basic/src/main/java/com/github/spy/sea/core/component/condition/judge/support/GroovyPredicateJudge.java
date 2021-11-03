package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.loader.LoadLevel;
import groovy.util.Eval;
import lombok.extern.slf4j.Slf4j;

/**
 * Groovy predicate judge.
 */
@Slf4j
@LoadLevel(name = "groovy")
public class GroovyPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        return (Boolean) Eval.me(conditionData.getParamName(), realData, conditionData.getParamValue());
    }
}
