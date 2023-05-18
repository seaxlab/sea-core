package com.github.seaxlab.core.dal.mybatis.plus;

import com.github.seaxlab.core.dal.mybatis.plus.service.User3Service;
import com.github.seaxlab.core.spring.tx.util.TxUtil;
import com.github.seaxlab.core.util.IdUtil;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/25
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class User2Service {

  private final User2Mapper user2Mapper;
  private final DataSourceTransactionManager dataSourceTransactionManager;
  //
  private final User3Service user3Service;

  @Transactional(rollbackFor = Exception.class)
  public void add() {
    User2 entity = new User2();
    entity.setName(RandomUtil.alphabetic(10));
    user2Mapper.insert(entity);
  }


  @Transactional(rollbackFor = Exception.class)
  public void add2() {
    User2 entity = new User2();
    entity.setName("1_" + RandomUtil.alphabetic(2));
    user2Mapper.insert(entity);

    TransactionStatus txStatus = TxUtil.begin(dataSourceTransactionManager);
    try {
      User2 entity2 = new User2();
      entity2.setName("2_" + IdUtil.getYYYYMMDDHHMMSSSSS());
      user2Mapper.insert(entity2);
      TxUtil.commit(dataSourceTransactionManager, txStatus);
    } catch (Exception e) {
      log.error("ee", e);
      TxUtil.rollback(dataSourceTransactionManager, txStatus);
    }
    //TxUtil.rollback(); // we will rollback.
    User2 entity3 = new User2();
    entity3.setName("3_" + RandomUtil.alphabetic(4));
    user2Mapper.insert(entity3);
  }

  @Transactional(rollbackFor = Exception.class)
  public void add3() {
    User2 entity = new User2();
    entity.setName("3_" + RandomUtil.alphabetic(2));
    user2Mapper.insert(entity);

    // nested
    user3Service.addInNested();
  }

  @Transactional(rollbackFor = Exception.class)
  public void add4() {
    User2 entity = new User2();
    entity.setName("4_" + RandomUtil.alphabetic(2));
    user2Mapper.insert(entity);

    // nested
    user3Service.addInNestedAndThrow();
  }

  @Transactional(rollbackFor = Exception.class)
  public void add5() {
    User2 entity = new User2();
    entity.setName("5_" + RandomUtil.alphabetic(2));
    user2Mapper.insert(entity);

    // nested
    user3Service.addInNestedAndThrow();
  }

}
