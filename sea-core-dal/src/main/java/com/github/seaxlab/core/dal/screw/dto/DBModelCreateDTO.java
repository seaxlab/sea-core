package com.github.seaxlab.core.dal.screw.dto;

import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.github.seaxlab.core.component.ssh.dto.SshConfig;
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
  private String groupName;
  private String outPutFileName;
  private String version;

  private String description;

  private Boolean cleanFlag;
  private Integer keepRecentCount = 3;

  private SshConfig sshConfig;


  private Extend extend = new Extend();

  @Data
  public static class Extend {

    private String finalOutPutFileName;
    private String finalOutPutDir;
  }
}
