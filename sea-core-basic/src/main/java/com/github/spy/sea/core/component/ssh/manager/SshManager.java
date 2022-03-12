package com.github.spy.sea.core.component.ssh.manager;

import com.github.spy.sea.core.component.ssh.dto.SshConfig;
import com.github.spy.sea.core.model.Result;

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

    Result<String> executeCmd(String cmd);

    Result<Boolean> rename(String oldPath, String newPath);

    Result<Boolean> upload(String localFilePath, String remoteDir);

    Result<Boolean> download(String remoteFilePath, String localDir);

    void destroy();
}
