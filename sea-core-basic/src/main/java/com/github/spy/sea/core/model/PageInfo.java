package com.github.spy.sea.core.model;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/14
 * @since 1.0
 */
@Slf4j
public class PageInfo implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public static PageInfo of(int pageNum, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
