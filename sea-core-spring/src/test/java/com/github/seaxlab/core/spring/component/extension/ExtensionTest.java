package com.github.seaxlab.core.spring.component.extension;

import com.github.seaxlab.core.spring.TestSpringConfig;
import com.github.seaxlab.core.spring.component.extension.executor.ExtensionExecutor;
import com.github.seaxlab.core.spring.component.extension.model.BizScenario;
import com.github.seaxlab.core.spring.component.extension.pay.ExtensionConst;
import com.github.seaxlab.core.spring.component.extension.pay.dto.PayDTO;
import com.github.seaxlab.core.spring.component.extension.pay.manager.PayManagerExtPt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestSpringConfig.class})
public class ExtensionTest {

  @Resource
  private ExtensionExecutor extensionExecutor;

  /**
   * 获取扩展点
   */
  @Test
  public void testGetExtension() throws Exception {
    //实践中更多是两层结构
    BizScenario scenario = BizScenario.of(ExtensionConst.PAY, ExtensionConst.USE_CASE_1, "WeiXin");

    PayManagerExtPt extPt = extensionExecutor.locateComponent(PayManagerExtPt.class, scenario);
    log.info("ext pointer={}", extPt);
    PayDTO dto = new PayDTO();
    extPt.pay(dto);
  }

  /**
   * 获取扩展点,执行默认逻辑
   */
  @Test
  public void testGetExtension2() throws Exception {
    BizScenario scenario = BizScenario.of(ExtensionConst.PAY, ExtensionConst.USE_CASE_1, "12");

    PayManagerExtPt extPt = extensionExecutor.locateComponent(PayManagerExtPt.class, scenario);
    log.info("ext pointer={}", extPt);
    PayDTO dto = new PayDTO();
    extPt.pay(dto);
  }

  /**
   * 获取扩展点,并执行
   */
  @Test
  public void testGetAndExecution() {
    BizScenario scenario = BizScenario.of(ExtensionConst.PAY, ExtensionConst.USE_CASE_1, "12");
    PayDTO dto = new PayDTO();
    //
    extensionExecutor.executeVoid(PayManagerExtPt.class, scenario, extension -> extension.pay(dto));
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
