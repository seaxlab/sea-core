package com.github.seaxlab.core.component.condition.data;

import com.github.seaxlab.core.component.condition.dto.ConditionContext;

/**
 * The interface Parameter data.
 */
public interface ParameterData {

    /**
     * Builder string.
     *
     * @param context   the exchange
     * @param paramName the param name
     * @return the string
     */
    default String builder(final ConditionContext context, final String paramName) {
        return "";
    }
}
