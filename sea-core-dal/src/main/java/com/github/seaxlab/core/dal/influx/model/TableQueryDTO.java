package com.github.seaxlab.core.dal.influx.model;

import lombok.Data;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/18
 * @since 1.0
 */
@Data
public class TableQueryDTO {
    private String bucket;
    private Map<String, String> tags;
}
