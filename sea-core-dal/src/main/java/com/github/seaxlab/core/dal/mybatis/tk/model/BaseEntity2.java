package com.github.seaxlab.core.dal.mybatis.tk.model;

import com.github.seaxlab.core.dal.mybatis.common.model.BaseEmptyEntity;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Data
public class BaseEntity2 extends BaseEmptyEntity {

    /**
     * 创建人
     */
    @Column(name = "`creator`")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "`editor`")
    private String editor;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;
}
