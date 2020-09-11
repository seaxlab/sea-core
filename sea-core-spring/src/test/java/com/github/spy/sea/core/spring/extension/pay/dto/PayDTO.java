package com.github.spy.sea.core.spring.extension.pay.dto;

import com.github.spy.sea.core.spring.extension.BizScenario;
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
