package com.github.seaxlab.core.support.oss.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Data
public class ObjectUrlDTO {

    private String bucket;
    private String key;
    private boolean customDomainFlag;

}
