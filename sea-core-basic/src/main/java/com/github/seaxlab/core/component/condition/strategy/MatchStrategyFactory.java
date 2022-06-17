package com.github.seaxlab.core.component.condition.strategy;

import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.enums.MatchModeEnum;
import com.github.seaxlab.core.loader.EnhancedServiceLoader;

import java.util.List;

/**
 * MatchStrategyFactory.
 */
public class MatchStrategyFactory {

    /**
     * New instance match strategy.
     *
     * @param strategy the strategy
     * @return the match strategy
     */
    public static MatchStrategy newInstance(final Integer strategy) {
        String matchMode = MatchModeEnum.getMatchModeByCode(strategy);
        return EnhancedServiceLoader.load(MatchStrategy.class, matchMode);
    }

    /**
     * Match boolean.
     *
     * @param strategy          the strategy
     * @param conditionDataList the condition data list
     * @param exchange          the exchange
     * @return the boolean
     */
    public static boolean match(final Integer strategy, final List<ConditionData> conditionDataList, final ConditionContext context) {
        return newInstance(strategy).match(context, conditionDataList);
    }
}
