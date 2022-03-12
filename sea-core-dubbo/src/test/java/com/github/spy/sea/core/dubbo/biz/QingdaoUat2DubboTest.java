package com.github.spy.sea.core.dubbo.biz;

import com.github.spy.sea.core.dubbo.BaseDubboTest;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/25
 * @since 1.0
 */
@Slf4j
public class QingdaoUat2DubboTest extends BaseDubboTest {

    private String VERSION = "1.0.0.uat";

    @Test
    public void run17() throws Exception {
        Long corpId = 261L;
        Long userId = 996138L;
        String patientId = "0101061699";

        String[] parameterTypes = new String[]{Long.class.getName(), Long.class.getName(), String.class.getName()};
        Object[] parameterArgs = new Object[]{corpId, userId, patientId};

        dto.setRegistryAddress(REG_QING_DAO_UAT2);
        dto.setInterfaceName("com.yuantu.user.service.PatientUsersDubboService");
        dto.setMethod("isHaveRelationByUserIdAndHisPatient");
        dto.setVersion(VERSION);
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);


        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }
}
