package com.github.seaxlab.core.dal.mybatis.plus.mapper;

import com.github.seaxlab.core.dal.mybatis.plus.entity.User2;
import com.github.seaxlab.core.dal.mybatis.plus.mapper.EnhanceBaseMapper;
import com.github.seaxlab.core.dal.mybatis.plus.po.UserQueryPO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
public interface User2Mapper extends EnhanceBaseMapper<User2> {

  Integer selectMaxAge();

  List<User2> queryUserList(@Param("po") UserQueryPO po);
}
