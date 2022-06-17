package com.github.seaxlab.core.spring.extension;

import com.github.seaxlab.core.spring.TestSpringConfig;
import com.github.seaxlab.core.spring.extension.pay.Constants;
import com.github.seaxlab.core.spring.extension.pay.PayService;
import com.github.seaxlab.core.spring.extension.pay.dto.PayDTO;
import com.github.seaxlab.core.spring.extension.pay.manager.PayManagerExtPt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestSpringConfig.class})
public class ExtensionTest {

    @Autowired
    private PayService payService;

    @Test
    public void payExtensionTest() {
        //1. Prepare
        PayDTO dto = new PayDTO();
        dto.setOrderNo("" + ThreadLocalRandom.current().nextInt(10000, 99999));
        BizScenario scenario = BizScenario.of(Constants.BIZ_1, Constants.USE_CASE_1, Constants.SCENARIO_1);
        dto.setBizScenario(scenario);

        //2. Execute
        payService.pay(dto);
    }


    @Resource
    private ExtensionExecutor extensionExecutor;

    @Test
    public void testGetExtension() throws Exception {
        BizScenario scenario = BizScenario.of(Constants.BIZ_1, Constants.USE_CASE_1, Constants.SCENARIO_1);

        PayManagerExtPt extPt = extensionExecutor.locateComponent(PayManagerExtPt.class, scenario);
        log.info("ext pointer={}", extPt);
    }


//    @Test
//    public void testBiz1UseCase1AddCustomerSuccess() {
//        //1. Prepare
//        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
//        CustomerCO customerCO = new CustomerCO();
//        customerCO.setCompanyName("alibaba");
//        customerCO.setSource(Constants.SOURCE_RFQ);
//        customerCO.setCustomerType(CustomerType.IMPORTANT);
//        addCustomerCmd.setCustomerCO(customerCO);
//        BizScenario scenario = BizScenario.valueOf(Constants.BIZ_1, Constants.USE_CASE_1);
//        addCustomerCmd.setBizScenario(scenario);
//
//        //2. Execute
//        Response response = customerService.addCustomer(addCustomerCmd);
//
//        //3. Expect Success
//        Assert.assertTrue(response.isSuccess());
//    }
//
//    @Test
//    public void testBiz1AddCustomerSuccess() {
//        //1. Prepare
//        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
//        CustomerCO customerCO = new CustomerCO();
//        customerCO.setCompanyName("jingdong");
//        customerCO.setSource(Constants.SOURCE_RFQ);
//        customerCO.setCustomerType(CustomerType.IMPORTANT);
//        addCustomerCmd.setCustomerCO(customerCO);
//        BizScenario scenario = BizScenario.valueOf(Constants.BIZ_1);
//        addCustomerCmd.setBizScenario(scenario);
//
//        //2. Execute
//        Response response = customerService.addCustomer(addCustomerCmd);
//
//        //3. Expect Success
//        Assert.assertTrue(response.isSuccess());
//    }
}
