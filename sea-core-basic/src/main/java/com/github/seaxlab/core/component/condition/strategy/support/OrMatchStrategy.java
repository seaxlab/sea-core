package com.github.seaxlab.core.component.condition.strategy.support;

import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudgeFactory;
import com.github.seaxlab.core.component.condition.strategy.AbstractMatchStrategy;
import com.github.seaxlab.core.component.condition.strategy.MatchStrategy;
import com.github.seaxlab.core.loader.LoadLevel;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * This is or match strategy.
 */
@Slf4j
@LoadLevel(name = "or")
public class OrMatchStrategy extends AbstractMatchStrategy implements MatchStrategy {

  @Override
  public Boolean match(final ConditionContext context, final List<ConditionData> conditionDataList) {
    checkCondition(conditionDataList);

    return conditionDataList.stream().anyMatch(
      condition -> PredicateJudgeFactory.judge(condition, buildRealData(context, condition)));
  }
}
