package com.github.seaxlab.core.component.condition.strategy;


import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.component.condition.dto.ConditionData;

import java.util.List;

/**
 * This is condition strategy.
 */
public interface MatchStrategy {

    /**
     * this is condition match.
     *
     * @param context           condition context
     * @param conditionDataList condition list.
     * @return true is match , false is not match.
     */
    Boolean match(ConditionContext context, List<ConditionData> conditionDataList);
}
