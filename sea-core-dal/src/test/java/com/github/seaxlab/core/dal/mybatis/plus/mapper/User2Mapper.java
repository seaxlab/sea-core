package com.github.seaxlab.core.dal.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.seaxlab.core.dal.mybatis.plus.entity.User2;
import com.github.seaxlab.core.dal.mybatis.plus.po.UserQueryPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
@Repository
public interface User2Mapper extends
  //EnhanceBaseMapper<User2>,
  BaseMapper<User2> {

  Integer selectMaxAge();

  List<User2> queryUserList(@Param("po") UserQueryPO po);
}
