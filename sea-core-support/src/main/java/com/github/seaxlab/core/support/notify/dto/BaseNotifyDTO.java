package com.github.seaxlab.core.support.notify.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */

@Data
public class BaseNotifyDTO implements Serializable {

    private String title;

    private String content;
}
