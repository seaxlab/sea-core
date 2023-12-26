package com.github.seaxlab.core.example.biz.unit;

import com.github.seaxlab.core.component.template.service.BaseOneBiz1Service;
import com.github.seaxlab.core.example.biz.unit.bo.UserAddReqBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/7/24
 * @since 1.0
 */
@Slf4j
@Component
public class UserAddUnitService extends BaseOneBiz1Service<UserAddReqBO> {

  @Override
  public String getBizName() {
    return "user add unit";
  }

  @Override
  public void handle(UserAddReqBO bo) {
    log.info("user add req unit bo={}", bo);
  }
}
