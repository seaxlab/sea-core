package com.github.spy.sea.core.component.condition.strategy.support;

import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudgeFactory;
import com.github.spy.sea.core.component.condition.strategy.AbstractMatchStrategy;
import com.github.spy.sea.core.component.condition.strategy.MatchStrategy;
import com.github.spy.sea.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * This is and match strategy.
 */
@Slf4j
@LoadLevel(name = "and")
public class AndMatchStrategy extends AbstractMatchStrategy implements MatchStrategy {

    @Override
    public Boolean match(final ConditionContext context, final List<ConditionData> conditionDataList) {
        checkCondition(conditionDataList);

        return conditionDataList
                .stream()
                .allMatch(condition -> PredicateJudgeFactory.judge(condition, buildRealData(context, condition)));
    }
}
