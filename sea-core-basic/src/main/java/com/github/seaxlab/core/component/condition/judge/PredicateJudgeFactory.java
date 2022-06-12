package com.github.seaxlab.core.component.condition.judge;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.loader.EnhancedServiceLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Predicate judge factory.
 */
@Slf4j
public class PredicateJudgeFactory {

    /**
     * New instance predicate judge.
     *
     * @param operator the operator
     * @return the predicate judge
     */
    public static PredicateJudge newInstance(final String operator) {
        return EnhancedServiceLoader.load(PredicateJudge.class, operator);
    }

    /**
     * judge request realData has by pass.
     *
     * @param conditionData condition data
     * @param realData      realData
     * @return is true pass   is false not pass
     */
    public static Boolean judge(final ConditionData conditionData, final String realData) {
        if (Objects.isNull(conditionData)) {
            log.warn("condition data is null, so end.");
            return false;
        }

        if (StringUtils.isBlank(realData)) {
            log.warn("real data is blank, so end.");
            return false;
        }

        return newInstance(conditionData.getOperator()).judge(conditionData, realData);
    }

}
