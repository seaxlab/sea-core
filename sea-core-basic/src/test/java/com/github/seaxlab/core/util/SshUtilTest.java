package com.github.seaxlab.core.util;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.dto.response.SshRespDTO;
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
    cfg.setSshPassword(getConfig("qd_jmzy_ssh_pwd"));

    cfg.setLocalPort(7777);

    cfg.setRemoteHost("192.168.60.21");
    cfg.setRemotePort(3306);

    SshRespDTO response = SshUtil.setUpPortForwarding(cfg);
    log.info("response={}", response);
  }

  @Test
  public void testExecuteCmd() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setSshHost("10.64.208.130");
    cfg.setSshPort(2222);
    cfg.setSshUserName("root");
    cfg.setSshPassword(getConfig("qd_jmzy_ssh_pwd"));
    String data = SshUtil.executeCmd(cfg, "pwd; ls -l");
    log.info("result={}", data);
  }

  @Test
  public void testUpload() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setSshHost("10.64.208.130");
    cfg.setSshPort(2222);
    cfg.setSshUserName("root");
    cfg.setSshPassword(getConfig("qd_jmzy_ssh_pwd"));
    boolean flag = SshUtil.upload(cfg, getUserHome() + "/test/gc/gc1.log", "/root");
    log.info("flag={}", flag);
  }

  @Test
  public void testDownload() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setSshHost("10.64.208.130");
    cfg.setSshPort(2222);
    cfg.setSshUserName("root");
    cfg.setSshPassword(getConfig("qd_jmzy_ssh_pwd"));
    boolean flag = SshUtil.download(cfg, "/root/gc1.log", getUserHome() + "/test/");
    log.info("flag={}", flag);
  }


}
