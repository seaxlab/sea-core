package com.github.seaxlab.core.component.validator.dto;

import com.github.seaxlab.core.component.validator.annotation.NotContainSpace;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Data
public class UserAddRequestDTO {

    @NotNull
    @NotContainSpace
    private String name;
}
