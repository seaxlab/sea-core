package com.github.spy.sea.core.spring.extension.pay;

import com.github.spy.sea.core.spring.extension.ExtensionExecutor;
import com.github.spy.sea.core.spring.extension.pay.dto.PayDTO;
import com.github.spy.sea.core.spring.extension.pay.manager.PayManagerExtPt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Override
    public void pay(PayDTO dto) {
        //do service
        extensionExecutor.executeVoid(PayManagerExtPt.class, dto.getBizScenario(), extension -> extension.pay(dto));
    }
}
