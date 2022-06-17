package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.resp.SshResp;
import com.github.seaxlab.core.model.Result;
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

        SshConfig cfg = new SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));

        cfg.setLocalPort(7777);

        cfg.setRemoteHost("192.168.60.21");
        cfg.setRemotePort(3306);

        Result<SshResp> result = SshUtil.setUpPortForwarding(cfg);
        if (result.isOk()) {
            log.info("result assigned port={}", result.getData().getAssignedPort());
        } else {
            log.info("fail result={}", result);
        }
    }

    @Test
    public void testExecuteCmd() throws Exception {
        SshConfig cfg = new SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));
        Result<String> result = SshUtil.executeCmd(cfg, "pwd; ls -l");
        log.info("result={}", result);
    }

    @Test
    public void testUpload() throws Exception {
        SshConfig cfg = new SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));
        Result result = SshUtil.upload(cfg, getUserHome() + "/test/gc/gc1.log", "/root");
        log.info("result={}", result);
    }

    @Test
    public void testDownload() throws Exception {
        SshConfig cfg = new SshConfig();
        cfg.setSshHost("10.64.208.130");
        cfg.setSshPort(2222);
        cfg.setSshUserName("root");
        cfg.setSshPassword(getPassword("qd_jmzy_ssh_pwd"));
        Result result = SshUtil.download(cfg, "/root/gc1.log", getUserHome() + "/test/");
        log.info("result={}", result);
    }


}
