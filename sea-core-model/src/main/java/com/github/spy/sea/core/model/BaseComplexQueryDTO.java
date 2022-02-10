package com.github.spy.sea.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 扁平的查询语句<br/>
 * and a=1 and c=2 or d=1
 * </p>
 * <p>
 * 不支持复杂条件：a=1 or (a=1 and b=1)
 * </p>
 *
 * @author spy
 * @version 1.0 2022/2/10
 * @since 1.0
 */
@Data
public class BaseComplexQueryDTO implements Serializable {

    private List<ConditionDTO> conditions;


    @Data
    public static class ConditionDTO {
        // and/or
        private String mode;
        // 条件key
        private String key;
        // 操作符 =<>in等等
        private String operator;
        // value
        private String value;
        // value type
        private String valueType;
    }
}
