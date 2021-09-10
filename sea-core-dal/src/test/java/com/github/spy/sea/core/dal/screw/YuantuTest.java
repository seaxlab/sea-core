package com.github.spy.sea.core.dal.screw;

import com.github.spy.sea.core.dal.screw.dto.DBModelCreateDTO;
import com.github.spy.sea.core.dal.screw.util.ScrewUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/10
 * @since 1.0
 */
@Slf4j
public class YuantuTest extends ScrewTest {
    @Test
    public void testHcrm() throws Exception {

        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rdsj56325f0xdcn346hn.mysql.rds.aliyuncs.com:3306/dc_device");
        dto.setUsername("dc_device");
        dto.setPassword("yuantu@123");

        dto.setOutPutFileName(getFileName("hcrm"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpMedicalSkill() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://10.122.2.17:1211/medical_skill?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("medical_skill");
        dto.setPassword("Yuantu123");

        dto.setOutPutFileName(getFileName("medical-skill"));

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpAolsee() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rdsj56325f0xdcn346hn.mysql.rds.aliyuncs.com/aolsee?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("aolsee");
        dto.setPassword("YTuat123");

        dto.setOutPutFileName(getFileName("aolsee"));

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpAccount() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rdsj56325f0xdcn346hn.mysql.rds.aliyuncs.com:3306/account_uat?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("account_uat");
        dto.setPassword("ACC_0unt2073");

        dto.setOutPutFileName(getFileName("account"));

        ScrewUtil.dump(dto);
    }
}
