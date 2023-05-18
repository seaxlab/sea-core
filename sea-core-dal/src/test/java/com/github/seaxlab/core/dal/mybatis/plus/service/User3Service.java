package com.github.seaxlab.core.dal.mybatis.plus.service;

import com.github.seaxlab.core.dal.mybatis.plus.User2;
import com.github.seaxlab.core.dal.mybatis.plus.User2Mapper;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/5/18
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class User3Service {

  private final User2Mapper user2Mapper;
  private final User4Service user4Service;

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void addInNested() {
    User2 entity = new User2();
    entity.setName("NESTED-3-" + RandomUtil.alphabetic(3));
    user2Mapper.insert(entity);

    user4Service.addInNested();
    throw new NullPointerException();
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void addInNestedAndThrow() {
    User2 entity = new User2();
    entity.setName("NESTED-3-" + RandomUtil.alphabetic(3));
    user2Mapper.insert(entity);
    //
    try {
      user4Service.addInNestedAndThrow();
    } catch (Exception e) {
      log.error("fail to execute user4service");
    }
  }
}
