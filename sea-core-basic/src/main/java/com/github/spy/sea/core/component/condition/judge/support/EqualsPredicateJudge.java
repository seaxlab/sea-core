package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Equals predicate judge.
 */
@Slf4j
@LoadLevel(name = "equals")
public class EqualsPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        return EqualUtil.isEq(realData, conditionData.getParamValue().trim());
    }
}
