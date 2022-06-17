package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Exclude predicate judge.
 */
@Slf4j
@LoadLevel(name = "exclude")
public class ExcludeOperatorJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        return !Objects.equals(realData, conditionData.getParamValue().trim());
    }
}
