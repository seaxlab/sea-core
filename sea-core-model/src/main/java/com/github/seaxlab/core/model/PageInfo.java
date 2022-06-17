package com.github.seaxlab.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息，从1开始
 *
 * @author spy
 * @version 1.0 2020/12/14
 * @since 1.0
 */
@Data
public class PageInfo implements Serializable {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * the same as pageIndex
     */
    private Integer pageNum = 1;

    private Integer pageSize = 10;

    /**
     * 每页最大记录数
     */
    private Integer pageMaxSize;

    private String orderBy;

    private String orderDirection;

    private String groupBy;

    public static PageInfo of(int pageNum, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    public Integer getPageNum() {
        if (pageNum < 1) {
            return 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (pageSize < 1) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public int getOffset() {
        return (getPageNum() - 1) * getPageSize();
    }

    public void setOrderDirection(String orderDirection) {
        if (orderDirection == null || orderDirection.isEmpty()) {
            return;
        }
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection.toLowerCase();
        }
    }
}
