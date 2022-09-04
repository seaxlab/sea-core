package com.github.seaxlab.core.model.layer.dto;

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
    private String name;
    private String type;
    private String remark;

    public static SimpleDTO create(String code, String name) {
        SimpleDTO dto = new SimpleDTO();
        dto.setCode(code);
        dto.setName(name);
        return dto;
    }

    public static SimpleDTO create(String code, String name, String type) {
        SimpleDTO dto = new SimpleDTO();
        dto.setCode(code);
        dto.setName(name);
        dto.setType(type);
        return dto;
    }

}
