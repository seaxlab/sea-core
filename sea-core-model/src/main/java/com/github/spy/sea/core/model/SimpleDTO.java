package com.github.spy.sea.core.model;

import lombok.Data;

/**
 * Simple dto
 *
 * @author spy
 * @version 1.0 2021/8/19
 * @since 1.0
 */
@Data
public class SimpleDTO extends DTO {

    private Long id;
    private String code;
    private String type;
    private String name;
    private String remark;

}
