package com.github.seaxlab.core.dal.mybatis.plus;

import com.github.seaxlab.core.spring.tx.util.TxUtil;
import com.github.seaxlab.core.util.IdUtil;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

/**
 * tx test
 *
 * @author spy
 * @version 1.0 2023/08/24
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TxTest extends BasePlusTest {

  @Autowired
  private DataSourceTransactionManager dataSourceTransactionManager;

  @Resource
  private User2Service user2Service;

  @Resource
  private User2Mapper user2Mapper;

  @Test
  public void testTx() throws Exception {
    TransactionStatus txStatus = TxUtil.begin(dataSourceTransactionManager);
    try {
      User2 entity = new User2();
      entity.setName("t_" + IdUtil.getYYYYMMDDHHMMSSSSS());
      user2Mapper.insert(entity);
      user2Service.add();
      TxUtil.commit(dataSourceTransactionManager, txStatus);
    } catch (Exception e) {
      log.error("ee", e);
      TxUtil.rollback(dataSourceTransactionManager, txStatus);
    }
  }

  @Test
  public void testTx2() throws Exception {
    user2Service.add21();
  }

  @Test
  public void testTx3() throws Exception {

  }

}
