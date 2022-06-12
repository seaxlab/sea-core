package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains predicate judge.
 */
@Slf4j
@LoadLevel(name = "contains")
public class ContainsPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        return realData.contains(conditionData.getParamValue().trim());
    }
}
