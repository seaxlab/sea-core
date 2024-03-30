package com.github.seaxlab.core.dal.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.seaxlab.core.dal.mybatis.plus.UserTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
@Slf4j
@Data
@TableName("user2")
public class User2 {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  private String code;
  private String name;
  private Integer age;
  private String email;

  private UserTypeEnum userType;
}
