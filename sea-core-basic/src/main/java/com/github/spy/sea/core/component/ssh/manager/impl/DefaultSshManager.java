package com.github.spy.sea.core.component.ssh.manager.impl;

import com.github.spy.sea.core.component.ssh.dto.SshConfig;
import com.github.spy.sea.core.component.ssh.manager.SshManager;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.model.BaseResult;
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
    public BaseResult<String> executeCmd(String cmd) {
        return null;
    }

    @Override
    public BaseResult<Boolean> rename(String oldPath, String newPath) {
        return null;
    }

    @Override
    public BaseResult<Boolean> upload(String localFilePath, String remoteDir) {
        return null;
    }

    @Override
    public BaseResult<Boolean> download(String remoteFilePath, String localDir) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
