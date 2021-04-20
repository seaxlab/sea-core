package com.github.spy.sea.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 设计有问题，总数和数据需要分开两个接口
 *
 * @author spy
 * @version 1.0 2019-05-13
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class PageInfoData implements Serializable {

    // 数据
    private List rows;

    // 总数
    private Long total;

}

