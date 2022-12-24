package com.github.seaxlab.core.spring.tx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/21
 * @since 1.0
 */
@Slf4j
public class TxService {

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <R> R execute(Functions.Func0<R> func) {
    return func.apply();
  }

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <T, R> R execute(Functions.Func1<T, R> func, T t) {
    return func.apply(t);
  }

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <T1, T2, R> R execute(Functions.Func2<T1, T2, R> func, T1 t1, T2 t2) {
    return func.apply(t1, t2);
  }

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <T1, T2, T3, R> R execute(Functions.Func3<T1, T2, T3, R> func, T1 t1, T2 t2, T3 t3) {
    return func.apply(t1, t2, t3);
  }

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <T1, T2, T3, T4, R> R execute(Functions.Func4<T1, T2, T3, T4, R> func, T1 t1, T2 t2, T3 t3, T4 t4) {
    return func.apply(t1, t2, t3, t4);
  }

  @Transactional(rollbackFor = Exception.class, timeout = 30)
  public <T1, T2, T3, T4, T5, R> R execute(Functions.Func5<T1, T2, T3, T4, T5, R> func, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
    return func.apply(t1, t2, t3, t4, t5);
  }


}
