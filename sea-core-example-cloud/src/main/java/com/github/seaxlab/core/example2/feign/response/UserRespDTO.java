package com.github.seaxlab.core.example2.feign.response;

import lombok.Data;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@Data
public class UserRespDTO {

    private Long userId;
    private String userName;
    private Date birthday;
}
