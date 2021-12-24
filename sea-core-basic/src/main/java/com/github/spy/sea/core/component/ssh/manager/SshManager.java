package com.github.spy.sea.core.component.ssh.manager;

import com.github.spy.sea.core.component.ssh.dto.SshConfig;
import com.github.spy.sea.core.model.BaseResult;

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

    BaseResult<String> executeCmd(String cmd);

    BaseResult<Boolean> rename(String oldPath, String newPath);

    BaseResult<Boolean> upload(String localFilePath, String remoteDir);

    BaseResult<Boolean> download(String remoteFilePath, String localDir);

    void destroy();
}
