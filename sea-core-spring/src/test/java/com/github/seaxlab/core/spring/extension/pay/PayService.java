package com.github.seaxlab.core.spring.extension.pay;

import com.github.seaxlab.core.spring.extension.pay.dto.PayDTO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
public interface PayService {

    void pay(PayDTO dto);
}
