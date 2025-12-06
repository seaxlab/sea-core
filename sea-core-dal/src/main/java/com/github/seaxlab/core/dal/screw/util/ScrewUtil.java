package com.github.seaxlab.core.dal.screw.util;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.github.seaxlab.core.dal.screw.dto.DBModelCreateDTO;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.IdUtil;
import com.github.seaxlab.core.util.IntegerUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.ObjectUtil;
import com.github.seaxlab.core.util.PathUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * screw util
 *
 * @author spy
 * @version 1.0 2021/8/25
 * @since 1.0
 */
@Slf4j
public final class ScrewUtil {

  public static final List<String> IGNORE_DATABASE = ListUtil.of("test", "mysql", "information_schema",
    "apolloconfigdb", "apolloportaldb");
  //
  public static final List<String> IGNORE_TABLE_PREFIX = ListUtil.of("test_", "bak_", "backup_", "zbak", "zzbak");

  //
  public static final List<String> IGNORE_TABLE_SUFFIX = ListUtil.of( //
    "_test", "_copy", "_bak", "_backup", "_prd", "_pro",
    //"_0","_00" //这里保留_0,_00防止库中没有后缀的表
    "_1", "_2", "_3", "_4", "_5", "_6", "_7", "_8", "_9", //
    "_01", "_02", "_03", "_04", "_05", "_06", "_07", "_08", "_09", //
    "_10", "_11", "_12", "_13", "_14", "_15", "_16", "_17", "_18", "_19", //
    "_20", "_21", "_22", "_23", "_24", "_25", "_26", "_27", "_28", "_29", //
    "_30", "_31", "_32", "_33", "_34", "_35", "_36", "_37", "_38", "_39" //
  );

  /**
   * dump file
   *
   * @param dto
   */
  public static void dump(DBModelCreateDTO dto) {
    check(dto);

    // screw config
    EngineConfig engineConfig = buildEngineConfig(dto);
    Configuration.ConfigurationBuilder builder = Configuration.builder().version(dto.getVersion())  // 版本
                                                              .description(dto.getDescription()) // 描述
                                                              .dataSource(buildDataSource(dto)) // 数据源
                                                              .engineConfig(engineConfig); // 引擎配置
    if (dto.getProcessConfig() != null) {
      builder.produceConfig(dto.getProcessConfig());
    }

    // generate database document
    new DocumentationExecute(builder.build()).execute();

    log.info("dump db model successfully. [{}/{} {}] ", engineConfig.getFileOutputDir(),
      dto.getExtend().getFinalOutPutFileName(),
      dto.getVersion());

    clean(dto);
  }


  //--------------------------private---------------------------------
  private static void check(DBModelCreateDTO dto) {
    if (StringUtil.isBlank(dto.getVersion())) {
      dto.setVersion("1.0.0");
    }
    if (StringUtil.isBlank(dto.getDescription())) {
      dto.setDescription("DB Model");
    }
    Precondition.checkNotEmpty(dto.getOutPutFileName(), "输出文件名不能为空");

    dto.getExtend().setFinalOutPutFileName(dto.getOutPutFileName() + "_" + IdUtil.getYYYYMMDDHHMM());

    //
    String baseDir = StringUtil.defaultIfBlank(dto.getOutPutDir(), PathUtil.getUserHome() + "/screw");
    String fileOutputDir = PathUtil.join(baseDir, dto.getGroupName(), dto.getModuleName());

    dto.getExtend().setFinalOutPutDir(fileOutputDir);
  }

  /**
   * 创建数据源
   */
  private static DataSource buildDataSource(DBModelCreateDTO dto) {
    String url = dto.getUrl();

    //TODO
    //if (dto.getSshConfig() != null) {
    //  log.warn("ssh proxy is not available now");
    //SshUtil.setUpPortForwarding(dto.getSshConfig());
    //
    //String endStr = dto.getUrl().substring(dto.getUrl().lastIndexOf("/"));
    //url = "jdbc:mysql://localhost:" + dto.getSshConfig().getLocalPort() + endStr;
    //}

    // Hikari config
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(StringUtil.defaultIfBlank(dto.getDriverClassName(), "com.mysql.jdbc.Driver"));
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(dto.getUsername());
    hikariConfig.setPassword(dto.getPassword());
    hikariConfig.addDataSourceProperty("useInformationSchema", "true"); // 设置可以获取 tables remarks 信息
    //
    return new HikariDataSource(hikariConfig);
  }

  /**
   * 创建 screw 的引擎配置
   */
  private static EngineConfig buildEngineConfig(DBModelCreateDTO dto) {

    return EngineConfig.builder() //
                       .fileOutputDir(dto.getExtend().getFinalOutPutDir()) // 生成文件路径
                       .openOutputDir(false) // 打开目录
                       .fileType(ObjectUtil.defaultIfNull(dto.getEngineFileType(), EngineFileType.HTML)) // 文件类型
                       .produceType(EngineTemplateType.freemarker) // 文件类型
                       .fileName(dto.getExtend().getFinalOutPutFileName()) // 自定义文件名称
                       .build();
  }

  /**
   * build process config
   */
  private static ProcessConfig buildProcessConfig() {
    return ProcessConfig.builder() //
                        .designatedTableName(Collections.emptyList())  // 根据名称指定表生成
                        .designatedTablePrefix(Collections.emptyList()) //根据表前缀生成
                        .designatedTableSuffix(Collections.emptyList()) // 根据表后缀生成
                        .ignoreTableName(Arrays.asList("test_user", "test_group")) // 忽略表名
                        .ignoreTablePrefix(Collections.singletonList("test_")) // 忽略表前缀
                        .ignoreTableSuffix(Collections.singletonList("_test")) // 忽略表后缀
                        .build();
  }

  private static void clean(DBModelCreateDTO dto) {
    if (!Boolean.TRUE.equals(dto.getCleanFlag())) {
      return;
    }
    try {
      int keepRecentCount = IntegerUtil.defaultIfNull(dto.getKeepRecentCount(), 3);
      log.info("try to clean old, keep recent count={}", keepRecentCount);
      String outFilePath = dto.getExtend().getFinalOutPutDir();
      File[] files = FileUtil.listFilesByRegExp(new File(outFilePath), dto.getOutPutFileName() + "_[0-9]+.html");
      if (files.length <= dto.getKeepRecentCount()) {
        return;
      }

      List<File> sortedFiles = Arrays.stream(files).sorted(Comparator.comparing(FileUtil::getCreateTime))
                                     .collect(Collectors.toList());

      List<File> removeFiles = ListUtil.page(sortedFiles, 1, sortedFiles.size() - keepRecentCount);
      removeFiles.forEach(file -> {
        file.delete();
        log.info("delete old file={}", file.getName());
      });
    } catch (Exception e) {
      log.warn("fail to clean old file", e);
    }
  }

}
