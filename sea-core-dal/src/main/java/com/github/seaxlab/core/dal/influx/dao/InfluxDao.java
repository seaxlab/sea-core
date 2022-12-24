package com.github.seaxlab.core.dal.influx.dao;

import com.github.seaxlab.core.dal.influx.model.BatchAddDTO;
import com.github.seaxlab.core.dal.influx.model.QueryDTO;
import com.github.seaxlab.core.dal.influx.model.Row;

import java.util.Comparator;
import java.util.List;

/**
 * 如果需要功能强大，还是直接原生api
 *
 * @author spy
 * @version 1.0 2021/3/18
 * @since 1.0
 */
public interface InfluxDao {

  Comparator<Row> SORT_VALUE_ASC = (o1, o2) -> Double.compare((Double) o1.getValue(), (Double) o2.getValue());
  Comparator<Row> SORT_VALUE_DESC = (o1, o2) -> Double.compare((Double) o2.getValue(), (Double) o1.getValue());
  Comparator<Row> SORT_NAME_ASC = (o1, o2) -> o1.getMetric().compareTo(o2.getMetric());
  Comparator<Row> SORT_NAME_DESC = (o1, o2) -> o2.getMetric().compareTo(o1.getMetric());

  /**
   * 批量插入
   *
   * @param data
   * @return
   */
  boolean batchInsert(BatchAddDTO dto);

  /**
   * 查询数据
   *
   * @param dto
   * @return
   */
  List<Row> querySum(QueryDTO dto);
}
