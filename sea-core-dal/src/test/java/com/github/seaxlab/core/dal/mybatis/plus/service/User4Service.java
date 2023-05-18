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
public class User4Service {

  private final User2Mapper user2Mapper;

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void addInNested() {
    log.info("add in nested");
    User2 entity = new User2();
    entity.setName("NESTED-4-" + RandomUtil.alphabetic(3));
    user2Mapper.insert(entity);
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void addInNestedAndThrow() {
    log.info("add in nested and throw");
    User2 entity = new User2();
    entity.setName("NESTED-4-" + RandomUtil.alphabetic(3));
    user2Mapper.insert(entity);
    throw new NullPointerException("");
  }
}
