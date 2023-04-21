package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.component.template.service.bo.MigrateReqBO;

/**
 * 迁移服务
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
public interface MigrateService {

  void execute(MigrateReqBO bo);

}
