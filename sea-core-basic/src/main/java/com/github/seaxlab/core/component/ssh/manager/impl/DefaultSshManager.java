package com.github.seaxlab.core.component.ssh.manager.impl;

import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.manager.SshManager;
import com.github.seaxlab.core.loader.LoadLevel;
import com.github.seaxlab.core.model.Result;
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
    public Result<String> executeCmd(String cmd) {
        return null;
    }

    @Override
    public Result<Boolean> rename(String oldPath, String newPath) {
        return null;
    }

    @Override
    public Result<Boolean> upload(String localFilePath, String remoteDir) {
        return null;
    }

    @Override
    public Result<Boolean> download(String remoteFilePath, String localDir) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
