package com.github.seaxlab.core.component.ssh.manager;

import com.github.seaxlab.core.component.ssh.dto.SshConfig;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
public interface SshManager {

  void init(SshConfig config);

  void connect();

  //TODO create port forwarding??

  String executeCmd(String cmd);

  boolean rename(String oldPath, String newPath);

  boolean upload(String localFilePath, String remoteDir);

  boolean download(String remoteFilePath, String localDir);

  void destroy();
}
