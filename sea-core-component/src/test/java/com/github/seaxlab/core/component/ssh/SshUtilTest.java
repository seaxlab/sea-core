package com.github.seaxlab.core.component.ssh;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

import com.github.seaxlab.core.component.BaseCoreTest;
import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.dto.response.SshRespDTO;
import com.github.seaxlab.core.component.ssh.util.SshUtil;
import com.github.seaxlab.core.util.PathUtil;
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
    cfg.setHost("10.64.208.130");
    cfg.setPort(2222);
    cfg.setUserName("root");
    cfg.setPassword(getConfig("qd_jmzy_ssh_pwd"));

    cfg.setLocalPort(7777);

    cfg.setRemoteHost("192.168.60.21");
    cfg.setRemotePort(3306);

    SshRespDTO response = SshUtil.setUpPortForwarding(cfg);
    log.info("response={}", response);
  }

  @Test
  public void testExecuteCmd() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setHost("10.64.208.130");
    cfg.setPort(2222);
    cfg.setUserName("root");
    cfg.setPassword(getConfig("qd_jmzy_ssh_pwd"));
    String data = SshUtil.executeCmd(cfg, "pwd; ls -l");
    log.info("result={}", data);
  }

  @Test
  public void testUpload() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setHost("10.64.208.130");
    cfg.setPort(2222);
    cfg.setUserName("root");
    cfg.setPassword(getConfig("qd_jmzy_ssh_pwd"));
    boolean flag = SshUtil.upload(cfg, PathUtil.getUserHome() + "/test/gc/gc1.log", "/root");
    log.info("flag={}", flag);
  }

  @Test
  public void testDownload() throws Exception {
    SshConfig cfg = new SshConfig();
    cfg.setHost("10.64.208.130");
    cfg.setPort(2222);
    cfg.setUserName("root");
    cfg.setPassword(getConfig("qd_jmzy_ssh_pwd"));
    boolean flag = SshUtil.download(cfg, "/root/gc1.log",PathUtil.getUserHome() + "/test/");
    log.info("flag={}", flag);
  }


}
