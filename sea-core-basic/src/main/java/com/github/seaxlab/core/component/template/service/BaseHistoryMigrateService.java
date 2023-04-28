package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.common.GlobalUtil;
import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.template.service.bo.HistoryMigrateReqBO;
import com.github.seaxlab.core.model.PageInfo;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * base history migrate service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("java:S2222")
public abstract class BaseHistoryMigrateService implements HistoryMigrateService {

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

  private static final int DEFAULT_MAX_ERROR_COUNT = 20;


  @Override
  public void execute() {
    log.info("migrate history begin, {}.", getBizType());

    String lockKey = getLockKey();
    if (StringUtil.isNotBlank(lockKey)) {
      LockService lockService = context.getBean(LockService.class);
      lockService.tryLock(lockKey, getBizType(), () -> handle());
      return;
    }
    //
    Collection<String> lockKeys = getLockKeys();
    if (CollectionUtil.isNotEmpty(lockKeys)) {
      LockService lockService = context.getBean(LockService.class);
      lockService.tryLock(lockKeys, getBizType(), () -> handle());
      return;
    }
    //
    handle();
  }

  private void handle() {
    HistoryMigrateReqBO bo = new HistoryMigrateReqBO();
    preHandle(bo);
    //
    try {
      process(bo);
      postHandle(bo);
    } finally {
      completeHandle(bo);
    }
  }


  private void process(HistoryMigrateReqBO bo) {
    int loopCount = 0;
    int errorCount = 0;

    boolean hasNextFlag = true;
    int pageSize = getPageSize() <= 0 ? CoreConst.PAGE_SIZE_200 : getPageSize();

    do {
      loopCount++;
      log.info("try to move {}, loop count={}", getBizType(), loopCount);

      PageInfo pageInfo = PageInfo.of(1, pageSize);
      queryByPage(bo, pageInfo);
      //
      Collection<Long> ids = bo.getExtend().getRecords();

      if (CollectionUtil.isEmpty(ids)) {
        log.warn("{} records is empty, so end.", getBizType());
        break;
      }
      if (ids.size() < pageSize) {
        log.info("ids size={}<{}, so end.", ids.size(), pageSize);
        hasNextFlag = false;
      }

      if (!moveToHistory(ids)) {
        errorCount++;
      }
      if (errorCount > DEFAULT_MAX_ERROR_COUNT) {
        log.warn("too many error count, plz check.");
        handleTooManyErrorException();
        break;
      }

      log.info("{} has next flag={}", getBizType(), hasNextFlag);
    } while (hasNextFlag);
  }


  private boolean moveToHistory(Collection<Long> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      log.warn("ids is empty.");
      return true;
    }

    boolean flag;

    try {
      transactionTemplate.execute(txStatus -> {
        log.info("try to delete {} ids={}", getBizType(), ids);
        int rowCount = moveToHistoryByIds(ids);
        GlobalUtil.checkDB(rowCount, MessageUtil.format("move to {} history", getBizType()));

        rowCount = deleteByIds(ids);
        GlobalUtil.checkDB(rowCount, MessageUtil.format("delete {}", getBizType()));

        return null;
      });
      flag = true;
    } catch (Exception e) {
      flag = false;
      log.error("fail to move to {} history, exception is ", getBizType(), e);
      handleMoveToHistoryException(e);
    }

    return flag;
  }


  // abstract method
  protected abstract String getBizType();


  public void preHandle(HistoryMigrateReqBO bo) {
  }

  public void postHandle(HistoryMigrateReqBO bo) {
  }

  public void completeHandle(HistoryMigrateReqBO bo) {
  }

  protected abstract void queryByPage(HistoryMigrateReqBO bo, PageInfo pageInfo);

  protected abstract int moveToHistoryByIds(Collection<Long> ids);

  protected abstract int deleteByIds(Collection<Long> ids);

  public String getLockKey() {
    return "";
  }

  public Collection<String> getLockKeys() {
    return SetUtil.empty();
  }

  public int getPageSize() {
    return CoreConst.PAGE_SIZE_200;
  }

  public void handleMoveToHistoryException(Exception e) {

  }

  public void handleTooManyErrorException() {

  }

}
