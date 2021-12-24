package com.github.spy.sea.core.dal.screw.dto;

import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.github.spy.sea.core.component.ssh.dto.SshConfig;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/25
 * @since 1.0
 */
@Data
public class DBModelCreateDTO {

    // db input
    // 默认mysql driver
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    //
    private ProcessConfig processConfig;

    // file output
    private EngineFileType engineFileType;
    private String outPutDir;
    private String outPutFileName;
    private String version;

    private String description;

    private SshConfig sshConfig;
}
