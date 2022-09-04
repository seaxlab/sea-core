package com.github.seaxlab.core.model.layer.vo;

import lombok.Data;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/6/2
 * @since 1.0
 */
@Data
public class BaseEntityResponseVO extends BaseResponseVO {

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String editor;

    /**
     * 更新时间
     */
    private Date updateTime;
}
