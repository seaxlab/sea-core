package com.github.spy.sea.core.dal.screw;

import com.github.spy.sea.core.dal.screw.dto.DBModelCreateDTO;
import com.github.spy.sea.core.dal.screw.util.ScrewUtil;
import com.github.spy.sea.core.util.SshUtil;
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
        dto.setPassword(getPassword("db_dc_device"));

        dto.setOutPutFileName(getFileName("hcrm"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpMedicalSkill() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://10.122.2.17:1211/medical_skill?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("medical_skill");
        dto.setPassword(getPassword("db_medical_skill"));

        dto.setOutPutFileName(getFileName("medical-skill"));

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpAolsee() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rdsj56325f0xdcn346hn.mysql.rds.aliyuncs.com/aolsee?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("aolsee");
        dto.setPassword(getPassword("db_aolsee"));

        dto.setOutPutFileName(getFileName("aolsee"));

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpAccount() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rdsj56325f0xdcn346hn.mysql.rds.aliyuncs.com:3306/account_uat?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("account_uat");
        dto.setPassword(getPassword("db_account_uat"));

        dto.setOutPutFileName(getFileName("account"));

        ScrewUtil.dump(dto);
    }

    @Test
    public void testDumpQueue() throws Exception {
        SshUtil.SshConfig cfg = new SshUtil.SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));

        cfg.setLocalPort(7777);

        cfg.setRemoteHost("192.168.60.21");
        cfg.setRemotePort(3306);

        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://192.168.60.21:3306/qms?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dto.setUsername("qms");
        dto.setPassword(getPassword("qd_jmzy_db_qms"));

        dto.setOutPutFileName(getFileName("qms"));
        dto.setSshConfig(cfg);
        ScrewUtil.dump(dto);
    }

}
