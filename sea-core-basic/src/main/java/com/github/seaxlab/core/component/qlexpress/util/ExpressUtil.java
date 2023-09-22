package com.github.seaxlab.core.component.qlexpress.util;

import com.github.seaxlab.core.component.qlexpress.factory.ExpressRunnerFactory;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * QLExpress util
 *
 * @author spy
 * @version 1.0 2023/9/21
 * @since 1.0
 */
@Slf4j
public final class ExpressUtil {

  private ExpressUtil() {

  }

  /**
   * check express text is valid
   *
   * @param express
   * @return
   */
  public static boolean check(String express) {
    ExpressRunner runner = new ExpressRunner();
    return runner.checkSyntax(express);
  }

  /**
   * 执行表达式
   *
   * @param express
   * @param context
   * @return
   */
  public static Object execute(String express, IExpressContext<String, Object> context) {
    return execute(express, context, null);
  }

  /**
   * 执行表达式
   *
   * @param express
   * @param context
   * @param errorList
   * @return
   */
  public static Object execute(String express, IExpressContext<String, Object> context, List<String> errorList) {
    try {
      return ExpressRunnerFactory.getInstance().execute(express, context, errorList, true, false);
    } catch (Exception e) {
      log.error("fail to execute ql express", e);
      throw new RuntimeException("表达式执行异常");
    }
  }

}
