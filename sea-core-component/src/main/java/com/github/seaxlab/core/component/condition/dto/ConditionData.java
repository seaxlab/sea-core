package com.github.seaxlab.core.component.condition.dto;

import lombok.Data;

/**
 * ConditionDTO.
 *
 * @since 2.0.0
 */
@Data
public class ConditionData {

    /**
     * param type (post  query  uri).
     */
    private String paramType;

    /**
     * 操作符
     * @see com.github.seaxlab.core.component.condition.enums.OperatorEnum
     */
    private String operator;

    /**
     * param name.
     */
    private String paramName;

    /**
     * param value.
     */
    private String paramValue;

}
