package com.github.spy.sea.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019/4/3
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysJobVO implements Serializable {

    private String jobName;

    private String jobGroup;

    private String desc;

    private String status;

    private String cronExpression;

}
