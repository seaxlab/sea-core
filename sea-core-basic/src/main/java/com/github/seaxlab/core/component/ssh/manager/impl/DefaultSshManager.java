package com.github.seaxlab.core.component.ssh.manager.impl;

import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.manager.SshManager;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Slf4j
@LoadLevel(name = "default")
public class DefaultSshManager implements SshManager {

  @Override
  public void init(SshConfig config) {

  }

  @Override
  public void connect() {

  }

  @Override
  public String executeCmd(String cmd) {
    return "";
  }

  @Override
  public boolean rename(String oldPath, String newPath) {
    return false;
  }

  @Override
  public boolean upload(String localFilePath, String remoteDir) {
    return false;
  }

  @Override
  public boolean download(String remoteFilePath, String localDir) {
    return false;
  }

  @Override
  public void destroy() {

  }
}
