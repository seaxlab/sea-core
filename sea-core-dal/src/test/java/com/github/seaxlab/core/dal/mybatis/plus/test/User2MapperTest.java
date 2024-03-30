package com.github.seaxlab.core.dal.mybatis.plus.test;

import com.github.seaxlab.core.dal.mybatis.plus.BasePlusTest;
import com.github.seaxlab.core.dal.mybatis.plus.mapper.User2Mapper;
import com.github.seaxlab.core.dal.mybatis.plus.po.UserQueryPO;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/11/19
 * @since 1.0
 */
@Slf4j
public class User2MapperTest extends BasePlusTest {

  @Resource
  private User2Mapper user2Mapper;

  @Test
  public void test20() throws Exception {
    UserQueryPO po = new UserQueryPO();
    user2Mapper.queryUserList(po);
    //select * from user2 WHERE 1=1
    //
    po.setFlag(true);
    user2Mapper.queryUserList(po);
    //select * from user2 WHERE 2=2
    //
    po.setFlag(false);
    user2Mapper.queryUserList(po);
    //select * from user2 WHERE 1=1 and 3=3
  }

}
