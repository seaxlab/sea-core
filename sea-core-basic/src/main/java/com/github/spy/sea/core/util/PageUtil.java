package com.github.spy.sea.core.util;

import com.github.pagehelper.PageInfo;
import com.github.spy.sea.core.model.BasePageQueryDTO;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.model.PageInfoData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 分页工具转换类
 *
 * @author spy
 * @version 1.0 2019/3/15
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


    /**
     * 转换成带分页信息的result
     *
     * @param data 最终数据
     * @return
     */
    public static BaseResult toPageInfoResult(List data) {
        BaseResult result = BaseResult.success();

        setPageInfo(data, result);

        return result;
    }

    /**
     * 转换成最终 BaseResult
     *
     * @param oldData 含pageInfo关系的数据
     * @param newData 最终转换后的数据
     * @return
     */
    public static BaseResult toPageInfoResult(List oldData, List newData) {
        PageInfo pageInfo = new PageInfo(oldData);

        return toPageInfoResult(newData, pageInfo.getTotal());
    }


    /**
     * 直接转换成分页列表
     *
     * @param data
     * @return
     */
    public static BaseResult toSimplePageInfoResult(List data) {
        return toPageInfoResult(data, data == null ? 0 : data.size());
    }

    /**
     * 转换成带分页信息的result
     *
     * @param data  最终数据
     * @param total 记录数
     * @return
     */
    public static BaseResult toPageInfoResult(List data, int total) {
        return toPageInfoResult(data, Long.valueOf(total));
    }

    /**
     * 转换成带分页信息的result
     *
     * @param data  最终数据
     * @param total 记录数
     * @return
     */
    public static BaseResult toPageInfoResult(List data, Long total) {
        PageInfoData pageInfoData = new PageInfoData();

        pageInfoData.setRows(data);
        pageInfoData.setTotal(total);

        BaseResult result = BaseResult.success();
        result.setData(pageInfoData);
        return result;
    }

    /**
     * 直接将pageInfo转BaseResult
     *
     * @param pageInfo
     * @return
     */
    public static BaseResult toPageInfoResult(PageInfo pageInfo) {

        return toPageInfoResult(pageInfo.getList(), pageInfo.getTotal());
    }


    /**
     * 设置分页信息
     *
     * @param data
     * @param result
     */
    public static void setPageInfo(List data, BaseResult result) {
        PageInfo page = new PageInfo(data);

        PageInfoData pageInfoData = new PageInfoData();

        pageInfoData.setRows(data);
        pageInfoData.setTotal(page.getTotal());

        result.setData(pageInfoData);
    }

    /**
     * 校验分页信息
     *
     * @param dto
     */
    public static void checkPageInfo(BasePageQueryDTO dto) {
        checkPageInfo(dto, 10);
    }

    /**
     * 校验分页信息
     *
     * @param dto
     * @param maxSize 每页记录数
     */
    public static void checkPageInfo(BasePageQueryDTO dto, int maxSize) {
        if (dto.getPage() == null || dto.getPage() < 1) {
            dto.setPage(1);
        }
        if (dto.getSize() == null || dto.getPage() < 1) {
            dto.setSize(maxSize);
        }
    }

}