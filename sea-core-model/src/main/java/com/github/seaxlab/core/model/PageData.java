package com.github.seaxlab.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据-响应
 *
 * @author spy
 * @version 1.0 2025/12/8
 * @since 1.0
 */
@Data
public class PageData<T> implements Serializable {
  /**
   * 当前页码
   */
  private Integer pageNum = 1;
  /**
   * 每页记录数
   */
  private Integer pageSize;
  /**
   * 总页数
   */
  private Integer pages = 0;
  /**
   * 总记录数
   */
  private Integer total = 0;

  /**
   * 数据
   */
  private final List<T> list = new ArrayList<>();


  /**
   * 创建一个空的分页数据
   *
   * @return
   */
  public static <T> PageData<T> empty() {
    PageData<T> pageResult = new PageData<>();
    pageResult.setTotal(0);
    pageResult.setPageNum(0);
    pageResult.setPageSize(0);
    pageResult.setPages(0);
    return pageResult;
  }
}
