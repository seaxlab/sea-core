package com.github.spy.sea.core.dal.mybatis.tk.mapper.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/13
 * @since 1.0
 */
@Slf4j
public class CheckExistProviderExt extends MapperTemplate {
    public CheckExistProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public boolean checkExist(MappedStatement ms) {

//        select 1 where limit 1
        //TODO
        return false;
    }

    public boolean checkExistByExample(MappedStatement ms) {
        //TODO
        return false;
    }
}
