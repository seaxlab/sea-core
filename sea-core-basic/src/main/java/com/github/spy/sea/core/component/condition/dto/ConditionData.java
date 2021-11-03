package com.github.spy.sea.core.component.condition.dto;

import com.github.spy.sea.core.enums.OperatorEnum;
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
     * {@linkplain OperatorEnum}.
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
