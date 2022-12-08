package com.github.seaxlab.core.dal.mybatis.tk.model;

import com.github.seaxlab.core.dal.mybatis.common.model.BaseEmptyEntity;
import java.util.Date;
import javax.persistence.Column;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Data
public class BaseEntity extends BaseEmptyEntity {

  @Column(name = "`create_user`")
  private String createUser;

  @Column(name = "`create_time`")
  private Date createTime;

  @Column(name = "`update_user`")
  private String updateUser;

  @Column(name = "`update_time`")
  private Date updateTime;
}
