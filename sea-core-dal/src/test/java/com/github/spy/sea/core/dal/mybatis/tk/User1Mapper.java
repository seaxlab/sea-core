package com.github.spy.sea.core.dal.mybatis.tk;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.CheckExistMapper;
import com.github.spy.sea.core.dal.mybatis.tk.mapper.InsertOrUpdateMapper;
import com.github.spy.sea.core.dal.mybatis.tk.mapper.SelectLatestOneMapper;
import com.github.spy.sea.core.dal.mybatis.tk.mapper.SelectMaxMapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
public interface User1Mapper extends
        BaseMapper<User1>,
        InsertOrUpdateMapper<User1>,
        SelectMaxMapper<User1>,
        CheckExistMapper<User1>,
        SelectLatestOneMapper<User1> {

    List<User1> queryHistory(@Param("delayMinute") int delayMinute);
}
