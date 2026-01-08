package com.github.seaxlab.core.spring.component.extension.pay.manager.impl;

import com.github.seaxlab.core.spring.component.extension.annotation.Extension;
import com.github.seaxlab.core.spring.component.extension.pay.ExtensionConst;
import com.github.seaxlab.core.spring.component.extension.pay.dto.PayDTO;
import com.github.seaxlab.core.spring.component.extension.pay.manager.PayManagerExtPt;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认行为
 *
 * @author spy
 * @version 1.0 2026/1/8
 * @since 1.0
 */
@Slf4j
@Extension(bizId = ExtensionConst.PAY)
public class NoopPayManagerExtPt implements PayManagerExtPt {
  @Override
  public void pay(PayDTO dto) {
    log.info("noop");
  }
}
