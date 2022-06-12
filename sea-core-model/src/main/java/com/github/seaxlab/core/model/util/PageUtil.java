package com.github.seaxlab.core.model.util;

import com.github.seaxlab.core.model.PageInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/8
 * @since 1.0
 */
@Slf4j
public final class PageUtil {

    private PageUtil() {
    }

    /**
     * 根据总记录数和每页记录数获取页码数
     *
     * @param totalCount
     * @param pageSize
     * @return 页码数
     */
    public static int getPageCount(int totalCount, int pageSize) {
        return totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1);
    }

    /**
     * 根据总记录数和每页记录数获取页码数
     *
     * @param totalCount total count
     * @param pageSize   page size of one page
     * @return total pages
     */
    public static long getPageCount(long totalCount, int pageSize) {
        return totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1);
    }

    // just my happy, no reason
    private static final int MAX_PAGE_SIZE = 10000;

    /**
     * 检查分页信息
     *
     * @param pageInfo
     */
    public static void check(PageInfo pageInfo) {
        if (pageInfo.getPageNum() == null || pageInfo.getPageNum() < 1) {
            pageInfo.setPageSize(1);
        }

        if (pageInfo.getPageSize() == null || pageInfo.getPageSize() < 1) {
            pageInfo.setPageSize(10);
        }

        if (pageInfo.getPageMaxSize() == null) {
            if (pageInfo.getPageSize() > MAX_PAGE_SIZE) {
                log.warn("pageSize[{}] > default[{}], so sent to {}", pageInfo.getPageNum(), MAX_PAGE_SIZE, MAX_PAGE_SIZE);
                pageInfo.setPageSize(MAX_PAGE_SIZE);
            }
        } else {
            if (pageInfo.getPageSize() > pageInfo.getPageMaxSize()) {
                log.warn("pageSize[{}] > pageMaxSize[{}], so sent to {}", pageInfo.getPageSize(), pageInfo.getPageMaxSize(), pageInfo.getPageMaxSize());
                pageInfo.setPageSize(pageInfo.getPageMaxSize());
            }
        }
    }
}
