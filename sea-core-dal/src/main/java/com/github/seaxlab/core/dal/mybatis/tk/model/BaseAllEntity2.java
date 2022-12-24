package com.github.seaxlab.core.dal.mybatis.tk.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class BaseAllEntity2 extends BaseEntity2 {

  /**
   * 备注
   */
  @Column(name = "`remark`")
  private String remark;

  /**
   * 是否启用 0否，1是（默认）
   */
  @Column(name = "`is_enabled`")
  private Boolean isEnabled;

  /**
   * 是否删除 0否（默认），1是
   */
  @Column(name = "`is_deleted`")
  private Boolean isDeleted;
}
