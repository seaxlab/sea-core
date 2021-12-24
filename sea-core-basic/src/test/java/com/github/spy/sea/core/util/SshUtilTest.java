package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Slf4j
public class SshUtilTest extends BaseCoreTest {

    @Test
    public void testConnect() throws Exception {

        SshUtil.SshConfig cfg = new SshUtil.SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));

        cfg.setLocalPort(7777);

        cfg.setRemoteHost("192.168.60.21");
        cfg.setRemotePort(3306);

        BaseResult<SshUtil.SshResp> result = SshUtil.connect(cfg);
        if (result.isOk()) {
            log.info("result assigned port={}", result.getData().getAssignedPort());
        } else {
            log.info("fail result={}", result);
        }
    }
}
