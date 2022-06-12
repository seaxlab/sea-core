package com.github.seaxlab.core.support.oss.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Data
public class ObjectQueryDTO {

    private String bucket;

    private String prefix;

    private int maxKeys;
}
