package com.github.seaxlab.core.component.condition;

import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.enums.MatchModeEnum;
import com.github.seaxlab.core.component.condition.strategy.MatchStrategyFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
@Slf4j
public class ConditionFactory {

    /**
     * 进行多条件匹配
     *
     * @param context
     * @param matchModeEnum
     * @param conditionDataList
     * @return
     */
    public static boolean match(final ConditionContext context,
                                final MatchModeEnum matchModeEnum,
                                final List<ConditionData> conditionDataList) {
        return MatchStrategyFactory.newInstance(matchModeEnum.getCode()).match(context, conditionDataList);
    }

    /**
     * 进行多条件匹配
     *
     * @param context
     * @param strategy
     * @param conditionDataList
     * @return
     */
    public static boolean match(final ConditionContext context,
                                final Integer strategy,
                                final List<ConditionData> conditionDataList) {
        return MatchStrategyFactory.newInstance(strategy).match(context, conditionDataList);
    }
}
