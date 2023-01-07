package com.github.seaxlab.core.spring.extension.pay.manager.impl;

import com.github.seaxlab.core.spring.extension.Extension;
import com.github.seaxlab.core.spring.extension.pay.Constants;
import com.github.seaxlab.core.spring.extension.pay.dto.PayDTO;
import com.github.seaxlab.core.spring.extension.pay.manager.PayManagerExtPt;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
@Slf4j
@Extension(bizId = Constants.BIZ_1, useCase = Constants.USE_CASE_1, scenario = Constants.SCENARIO_1)
public class WeixinPayManagerExtPt implements PayManagerExtPt {
  @Override
  public void pay(PayDTO dto) {
    log.info("orderNo={}", dto.getOrderNo());
    log.info("wei xin pay...");
  }
}
