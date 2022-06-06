package com.github.spy.sea.core.example.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Data
public class Param1DTO {
    private String code;
    private List<String> roles;
}
