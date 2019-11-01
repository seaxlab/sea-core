package com.github.spy.sea.core.domain;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/29
 * @since 1.0
 */
@Data
public class User {

    private Long id;
    private String name;
    List<Role> roles;

    private boolean isSuc;
    private Boolean isUsed;
}
