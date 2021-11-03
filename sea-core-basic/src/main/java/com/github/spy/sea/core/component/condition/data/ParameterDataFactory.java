package com.github.spy.sea.core.component.condition.data;


import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.loader.EnhancedServiceLoader;

/**
 * The type Parameter data factory.
 */
public class ParameterDataFactory {

    /**
     * New instance parameter data.
     *
     * @param paramType the param type
     * @return the parameter data
     */
    public static ParameterData newInstance(final String paramType) {
        return EnhancedServiceLoader.load(ParameterData.class, paramType);
    }

    /**
     * Builder data string.
     *
     * @param paramType the param type
     * @param paramName the param name
     * @param context   the exchange
     * @return the string
     */
    public static String builderData(final String paramType, final String paramName, final ConditionContext context) {
        return newInstance(paramType).builder(context, paramName);
    }
}
