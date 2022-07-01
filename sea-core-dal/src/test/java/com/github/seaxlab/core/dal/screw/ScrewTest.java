package com.github.seaxlab.core.dal.screw;

import com.github.seaxlab.core.dal.BaseCoreDalTest;
import com.github.seaxlab.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Base screw test
 *
 * @author spy
 * @version 1.0 2021/8/25
 * @since 1.0
 */
@Slf4j
public class ScrewTest extends BaseCoreDalTest {

    protected String getFileName(String prefix) {
        return prefix + "_" + IdUtil.getYYYYMMDDHHMM();
    }

    protected String getDBUrl(String baseUrl) {
        return baseUrl + "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false";
    }

    // simple dump

//    public void testDumpAccount() throws Exception {
//        DBModelCreateDTO dto = new DBModelCreateDTO();
//        dto.setUrl(getDBUrl("jdbc:mysql://url:3306/db"));
//        dto.setUsername("account_uat");
//        dto.setPassword(getPassword("db_account_uat"));
//
//        dto.setOutPutFileName(getFileName("account"));
//
//        ScrewUtil.dump(dto);
//    }

    // dump by ssh

//    public void testDumpQueue() throws Exception {
//        SshConfig cfg = new SshConfig();
//        cfg.setSshHost("");
//        cfg.setSshPort(2222);
//        cfg.setSshUserName("");
//        cfg.setSshPassword(getPassword(""));
//
//        cfg.setLocalPort(7777);
//
//        cfg.setRemoteHost("");
//        cfg.setRemotePort(3306);
//
//        DBModelCreateDTO dto = new DBModelCreateDTO();
//        dto.setUrl(getDBUrl("jdbc:mysql://xx/db"));
//        dto.setUsername("db");
//        dto.setPassword(getPassword("db"));
//
//        dto.setOutPutFileName(getFileName("db"));
//        dto.setSshConfig(cfg);
//        ScrewUtil.dump(dto);
//    }

}
