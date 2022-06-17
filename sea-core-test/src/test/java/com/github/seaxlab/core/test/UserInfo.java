package com.github.seaxlab.core.test;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/16
 * @since 1.0
 */
@Data
public class UserInfo {
    private Long id;
    private String name;
    private int age;
    private boolean isSuperAdmin;

    private String startDate;
    private String endDate;
    private Boolean flag;
    private int threads;
}
