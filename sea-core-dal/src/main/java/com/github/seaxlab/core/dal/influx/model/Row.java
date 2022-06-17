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
public class Row {
    private String metric;
    private Object value;
    /**
     * 时间点毫秒
     */
    private long time;
    private Map<String, Object> tags;
    private Map<String, Object> fields;
}
