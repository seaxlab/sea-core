package com.github.seaxlab.core.dal.influx.model;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/19
 * @since 1.0
 */
@Data
public class BatchAddDTO {
    private String org;
    private String bucket;
    private List<Row> data;
}
