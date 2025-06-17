package com.github.seaxlab.core.spring.component.extension.pay.dto;

import com.github.seaxlab.core.spring.component.extension.model.BizScenario;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
@Data
public class PayDTO {

  private String orderNo;

  private BizScenario bizScenario;
}
