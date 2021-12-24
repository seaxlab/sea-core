package com.github.spy.sea.core.dal.screw.util;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.github.spy.sea.core.dal.screw.dto.DBModelCreateDTO;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.util.ObjectUtil;
import com.github.spy.sea.core.util.PathUtil;
import com.github.spy.sea.core.util.SshUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/25
 * @since 1.0
 */
@Slf4j
public final class ScrewUtil {

    public static void dump(DBModelCreateDTO dto) {
        // set default
        if (StringUtil.isBlank(dto.getVersion())) {
            dto.setVersion("1.0.0");
        }
        if (StringUtil.isBlank(dto.getDescription())) {
            dto.setDescription("DB Model");
        }


        // 创建 screw 的配置
        EngineConfig engineConfig = buildEngineConfig(dto);
        Configuration.ConfigurationBuilder builder = Configuration.builder()
                                                                  .version(dto.getVersion())  // 版本
                                                                  .description(dto.getDescription()) // 描述
                                                                  .dataSource(buildDataSource(dto)) // 数据源
                                                                  .engineConfig(engineConfig); // 引擎配置
        if (dto.getProcessConfig() != null) {
            builder.produceConfig(dto.getProcessConfig());
        }

        // 执行 screw，生成数据库文档
        new DocumentationExecute(builder.build()).execute();

        log.info("dump db model successfully. [{}/{} {}] ", engineConfig.getFileOutputDir(), dto.getOutPutFileName(), dto.getVersion());
    }

    /**
     * 创建数据源
     */
    private static DataSource buildDataSource(DBModelCreateDTO dto) {
        String url = dto.getUrl();
        if (dto.getSshConfig() != null) {
            BaseResult result = SshUtil.connect(dto.getSshConfig());
            if (result.isFail()) {
                ExceptionHandler.publishMsg("fail to build ssh connection");
            }
            String endStr = dto.getUrl().substring(dto.getUrl().lastIndexOf("/"));
            url = "jdbc:mysql://localhost:" + dto.getSshConfig().getLocalPort() + endStr;
        }

        // 创建 HikariConfig 配置类
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(StringUtil.defaultIfBlank(dto.getDriverClassName(), "com.mysql.jdbc.Driver"));
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(dto.getUsername());
        hikariConfig.setPassword(dto.getPassword());
        hikariConfig.addDataSourceProperty("useInformationSchema", "true"); // 设置可以获取 tables remarks 信息
        // 创建数据源
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 创建 screw 的引擎配置
     */
    private static EngineConfig buildEngineConfig(DBModelCreateDTO dto) {
        return EngineConfig.builder()
                           .fileOutputDir(StringUtil.defaultIfBlank(dto.getOutPutDir(), PathUtil.getUserHome() + "/screw")) // 生成文件路径
                           .openOutputDir(false) // 打开目录
                           .fileType(ObjectUtil.defaultIfNull(dto.getEngineFileType(), EngineFileType.HTML)) // 文件类型
                           .produceType(EngineTemplateType.freemarker) // 文件类型
                           .fileName(StringUtil.defaultIfBlank(dto.getOutPutFileName(), "db-model")) // 自定义文件名称
                           .build();
    }

    /**
     * 创建 screw 的处理配置，一般可忽略
     * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
     */
    private static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                            .designatedTableName(Collections.<String>emptyList())  // 根据名称指定表生成
                            .designatedTablePrefix(Collections.<String>emptyList()) //根据表前缀生成
                            .designatedTableSuffix(Collections.<String>emptyList()) // 根据表后缀生成
                            .ignoreTableName(Arrays.asList("test_user", "test_group")) // 忽略表名
                            .ignoreTablePrefix(Collections.singletonList("test_")) // 忽略表前缀
                            .ignoreTableSuffix(Collections.singletonList("_test")) // 忽略表后缀
                            .build();
    }


}
