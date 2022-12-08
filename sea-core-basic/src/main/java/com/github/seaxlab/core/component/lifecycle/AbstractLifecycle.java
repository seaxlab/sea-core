package com.github.seaxlab.core.component.lifecycle;


import com.github.seaxlab.core.exception.BaseAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianghang 2013-10-23 下午5:19:38
 * @since 5.0.0
 */
public class AbstractLifecycle implements Lifecycle {

  private static Logger _log = LoggerFactory.getLogger(AbstractLifecycle.class.getClass());

  protected final Object lock = new Object();
  protected volatile boolean isInited = false;

  @Override
  public void init() throws BaseAppException {
    synchronized (lock) {
      if (isInited()) {
        _log.info("has inited.");
        return;
      }

      try {
        doInit();
        isInited = true;
      } catch (Exception e) {
        // 出现异常调用destory方法，释放
        try {
          doDestroy();
        } catch (Exception e1) {
          // ignore
        }
        throw new BaseAppException(e);
      }
    }
  }

  @Override
  public void destroy() throws BaseAppException {
    synchronized (lock) {
      if (!isInited()) {
        return;
      }

      doDestroy();
      isInited = false;
    }
  }

  @Override
  public boolean isInited() {
    return isInited;
  }

  protected void doInit() throws BaseAppException {
  }

  protected void doDestroy() throws BaseAppException {
  }

}
