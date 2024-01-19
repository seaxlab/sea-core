package com.github.seaxlab.core.dal.mybatis.plus.test;

import com.github.seaxlab.core.dal.mybatis.plus.BasePlusTest;
import com.github.seaxlab.core.dal.mybatis.plus.dao.User2Dao;
import com.github.seaxlab.core.dal.mybatis.plus.entity.User2;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/1/19
 * @since 1.0
 */
@Slf4j
public class SaveTest extends BasePlusTest {

  @Resource
  private User2Dao user2Dao;

  @Test
  public void test17() throws Exception {
    User2 record = new User2();
    record.setId(194L);
    record.setName("1-1");
//    record.setCode("1-1");
    user2Dao.save(record);
  }

  @Test
  public void testSaveBatch() throws Exception {
    List<User2> users = new ArrayList<>();

    //
    User2 po = user2Dao.getById(194L);
    users.add(po);

    User2 record;
    record = new User2();
    record.setName("1-2");
    users.add(record);

    user2Dao.saveBatch(users);
  }

}
