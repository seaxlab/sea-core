package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.template.service.bo.DataSnapshotReqBO;
import com.github.seaxlab.core.model.PageInfo;
import com.github.seaxlab.core.util.CollectionUtil;
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
public abstract class BaseDataSnapshotService implements HistoryCleanService {

  private ApplicationContext context;

  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }


  private static final int DEFAULT_MAX_ERROR_COUNT = 20;


  @Override
  public void execute() {
    log.info("snapshot begin, {}.", getBizType());

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
    DataSnapshotReqBO bo = new DataSnapshotReqBO();
    preHandle(bo);

    try {
      process(bo);
      postHandle(bo);
    } finally {
      completeHandle(bo);
    }
  }

  private void process(DataSnapshotReqBO bo) {
    boolean hasNextFlag = true;
    int pageNum = 1;
    int pageSize = getPageSize() <= 0 ? CoreConst.PAGE_SIZE_200 : getPageSize();

    do {
      log.info("try to snapshot {}, pageNum={}", getBizType(), pageNum);

      PageInfo pageInfo = PageInfo.of(pageNum, pageSize);
      queryByPage(bo, pageInfo);
      //
      Collection<?> data = bo.getExtend().getRecords();

      if (CollectionUtil.isEmpty(data)) {
        log.warn("{} records is empty, so end.", getBizType());
        break;
      }

      if (data.size() < pageSize) {
        log.info("{}, {}<{}, so end.", getBizType(), data.size(), pageSize);
        hasNextFlag = false;
      } else {
        pageNum++;
      }

      saveSnapshot(data);
      //
      log.info("{} has next flag={}", getBizType(), hasNextFlag);
    } while (hasNextFlag);
  }


  // abstract method
  protected abstract String getBizType();


  public void preHandle(DataSnapshotReqBO bo) {
  }

  public void postHandle(DataSnapshotReqBO bo) {

  }

  public void completeHandle(DataSnapshotReqBO bo) {
  }

  /**
   * 分页查询
   *
   * @param bo
   * @param pageInfo
   */
  protected abstract void queryByPage(DataSnapshotReqBO bo, PageInfo pageInfo);

  /**
   * 生成快照
   *
   * @param data
   */
  protected abstract void saveSnapshot(Collection<?> data);

  public String getLockKey() {
    return "";
  }

  public Collection<String> getLockKeys() {
    return SetUtil.empty();
  }

  public int getPageSize() {
    return CoreConst.PAGE_SIZE_200;
  }


}
