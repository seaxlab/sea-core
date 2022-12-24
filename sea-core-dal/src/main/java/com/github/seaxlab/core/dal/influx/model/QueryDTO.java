package com.github.seaxlab.core.dal.influx.model;

import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/18
 * @since 1.0
 */
@Data
public class QueryDTO {

  private String bucket;
  private Date start;
  private Date stop;
  private Map<String, Object> tags;


  private List<String> groupBy;
  private Comparator<Row> comparator;
}
