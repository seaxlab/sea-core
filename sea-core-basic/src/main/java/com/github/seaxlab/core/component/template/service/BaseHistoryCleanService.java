package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.common.GlobalUtil;
import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.template.service.bo.HistoryCleanReqBO;
import com.github.seaxlab.core.model.PageInfo;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * base history clean service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("java:S2222")
public abstract class BaseHistoryCleanService implements HistoryCleanService {

  private ApplicationContext context;

  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }


  private static final int DEFAULT_MAX_ERROR_COUNT = 20;


  @Override
  public void execute() {
    log.info("history clean begin, {}.", getBizType());

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
    int loopCount = 0;
    int errorCount = 0;

    boolean hasNextFlag;
    HistoryCleanReqBO bo = new HistoryCleanReqBO();
    beforeLoop(bo);

    do {
      loopCount++;
      log.info("try to clean {}, loop count={}", getBizType(), loopCount);

      int pageSize = getPageSize() <= 0 ? CoreConst.PAGE_SIZE_200 : getPageSize();
      PageInfo pageInfo = PageInfo.of(1, pageSize);
      queryByPage(bo, pageInfo);

      if (CollectionUtil.isEmpty(bo.getExtend().getRecords())) {
        log.warn("{} records is empty, so end.", getBizType());
        break;
      }
      Collection<Long> ids = bo.getExtend().getRecords();

      if (!historyDelete(ids)) {
        errorCount++;
      }
      if (errorCount > DEFAULT_MAX_ERROR_COUNT) {
        log.warn("too many error count, plz check.");
        handleTooManyErrorException();
        break;
      }

      hasNextFlag = bo.getExtend().hasNext();
      log.info("{} has next flag={}", getBizType(), hasNextFlag);
    } while (hasNextFlag);
  }


  private boolean historyDelete(Collection<Long> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      log.warn("ids is empty.");
      return true;
    }

    boolean flag;

    try {
      log.info("try to delete {} ids={}", getBizType(), ids);
      int rowCount = deleteByIds(ids);
      GlobalUtil.checkDB(rowCount, MessageUtil.format("delete {}", getBizType()));
      flag = true;
    } catch (Exception e) {
      flag = false;
      log.error("fail to delete history, {}, exception is ", getBizType(), e);
      handleDeleteException(e);
    }

    return flag;
  }


  // abstract method
  protected abstract String getBizType();


  /**
   * 循环之前
   *
   * @param bo biz object
   */
  public void beforeLoop(HistoryCleanReqBO bo) {
  }

  /**
   * 分页查询
   *
   * @param bo
   * @param pageInfo
   */
  protected abstract void queryByPage(HistoryCleanReqBO bo, PageInfo pageInfo);

  /**
   * 删除数据
   *
   * @param ids
   * @return
   */
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

  public void handleDeleteException(Exception e) {

  }

  public void handleTooManyErrorException() {

  }

}
