package com.github.seaxlab.core.support.oss.dto;

import com.github.seaxlab.core.support.oss.enums.HttpMethodEnum;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Data
public class ObjectSignUrlDTO {
    private String bucket;
    private String key;
    private long expireSeconds;

    private HttpMethodEnum httpMethod;
}
