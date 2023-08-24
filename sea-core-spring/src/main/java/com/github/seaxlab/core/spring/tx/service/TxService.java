package com.github.seaxlab.core.spring.tx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tx service
 *
 * @author spy
 * @version 1.0 2022/6/21
 * @since 1.0
 */
@Slf4j
public class TxService {

  //------------------------------- execute in normal tx begin -------------------------
  @Transactional(rollbackFor = Exception.class)
  public void execute(Runnable runnable) {
    runnable.run();
  }

  @Transactional(rollbackFor = Exception.class)
  public <R> R execute(Functions.Func0<R> func) {
    return func.apply();
  }

  @Transactional(rollbackFor = Exception.class)
  public <T, R> R execute(Functions.Func1<T, R> func, T t) {
    return func.apply(t);
  }

  @Transactional(rollbackFor = Exception.class)
  public <T1, T2, R> R execute(Functions.Func2<T1, T2, R> func, T1 t1, T2 t2) {
    return func.apply(t1, t2);
  }

  @Transactional(rollbackFor = Exception.class)
  public <T1, T2, T3, R> R execute(Functions.Func3<T1, T2, T3, R> func, T1 t1, T2 t2, T3 t3) {
    return func.apply(t1, t2, t3);
  }
  //------------------------------- execute in normal tx end-------------------------


  //---------------------------------execute in new tx begin-------------------------
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public void executeInNew(Runnable runnable) {
    log.info("execute in new tx.");
    runnable.run();
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public <R> R executeInNew(Functions.Func0<R> func) {
    return func.apply();
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public <T, R> R executeInNew(Functions.Func1<T, R> func, T t) {
    return func.apply(t);
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public <T1, T2, R> R executeInNew(Functions.Func2<T1, T2, R> func, T1 t1, T2 t2) {
    return func.apply(t1, t2);
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public <T1, T2, T3, R> R executeInNew(Functions.Func3<T1, T2, T3, R> func, T1 t1, T2 t2, T3 t3) {
    return func.apply(t1, t2, t3);
  }
  //---------------------------------execute in new end -------------------------

  //-----------------------------------execute in nested tx begin--------------------------------
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void executeInNested(Runnable runnable) {
    runnable.run();
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public <R> R executeInNested(Functions.Func0<R> func) {
    return func.apply();
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public <T, R> R executeInNested(Functions.Func1<T, R> func, T t) {
    return func.apply(t);
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public <T1, T2, R> R executeInNested(Functions.Func2<T1, T2, R> func, T1 t1, T2 t2) {
    return func.apply(t1, t2);
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public <T1, T2, T3, R> R executeInNested(Functions.Func3<T1, T2, T3, R> func, T1 t1, T2 t2, T3 t3) {
    return func.apply(t1, t2, t3);
  }
  //-----------------------------------execute in nested tx end--------------------------------


}
