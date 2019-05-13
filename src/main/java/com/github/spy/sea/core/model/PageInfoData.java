package com.github.spy.sea.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019-05-13
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoData implements Serializable {

    // 数据
    private List rows;

    // 总数
    private Long total;

}

