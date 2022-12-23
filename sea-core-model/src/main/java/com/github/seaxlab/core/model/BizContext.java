package com.github.seaxlab.core.model;

import java.util.HashMap;

/**
 * biz Context
 *
 * @author spy
 * @version 1.0 2019/9/11
 * @since 1.0
 */
public class BizContext extends HashMap {

  public static BizContext create() {
    return new BizContext();
  }
}
