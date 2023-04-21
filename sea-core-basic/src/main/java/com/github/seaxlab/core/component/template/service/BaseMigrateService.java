package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.template.service.bo.MigrateReqBO;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * base migrate service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
@Slf4j
public abstract class BaseMigrateService implements MigrateService {

  private ApplicationContext context;
  private TransactionTemplate transactionTemplate;

  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  @Autowired
  public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
    this.transactionTemplate = transactionTemplate;
  }


  @Override
  public void execute(MigrateReqBO bo) {
    log.info("migrate begin, {}.", getBizType());

    LockService lockService = context.getBean(LockService.class);
    lockService.tryLock(getLockKey(), getBizType(), () -> handle(bo));
  }

  private void handle(MigrateReqBO bo) {
  }


  // abstract method
  protected abstract String getBizType();

  public String getLockKey() {
    return StringUtil.empty();
  }

  public Collection<String> getLockKeys() {
    return SetUtil.empty();
  }
}
