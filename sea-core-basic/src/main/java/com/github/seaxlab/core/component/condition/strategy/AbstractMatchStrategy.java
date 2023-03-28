package com.github.seaxlab.core.component.condition.strategy;

import com.github.seaxlab.core.component.condition.data.ParameterDataFactory;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.exception.Precondition;
import java.util.List;

/**
 * AbstractMatchStrategy.
 */
public abstract class AbstractMatchStrategy {

    /**
     * Build real data string.
     *
     * @param context   the context
     * @param condition the condition
     * @return the string
     */
    public String buildRealData(final ConditionContext context, final ConditionData condition) {
        return ParameterDataFactory.builderData(condition.getParamType(), condition.getParamName(), context);
    }

    protected void checkCondition(List<ConditionData> conditionDataList) {
        Precondition.checkNotEmpty(conditionDataList, "condition data cannot be null");

        for (ConditionData conditionData : conditionDataList) {
            checkCondition(conditionData);
        }
    }

    protected void checkCondition(ConditionData conditionData) {
        Precondition.checkNotNull(conditionData, "condition data cannot be null.");
        Precondition.checkNotBlank(conditionData.getOperator(), "条件运算符不能为空");
        Precondition.checkNotBlank(conditionData.getParamType(), "条件参数类型不能为空");
        Precondition.checkNotBlank(conditionData.getParamName(), "条件参数名称不能为空");
        Precondition.checkNotBlank(conditionData.getParamValue(), "条件参数值不能为空");
    }
}
