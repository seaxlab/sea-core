package com.github.seaxlab.core.component.qlexpress.factory;

import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;

/**
 * Express runner factory
 *
 * @author spy
 * @version 1.0 2023/9/21
 * @since 1.0
 */
@Slf4j
public final class ExpressRunnerFactory {

  private volatile static ExpressRunner instance;

  public static ExpressRunner getInstance() {
    if (instance == null) {
      synchronized (ExpressRunnerFactory.class) {
        if (instance == null) {
          ExpressRunner runner = new ExpressRunner();
//          initFunctionOfServiceMethods(runner);
          instance = runner;
        }
      }
    }
    return instance;
  }


}
