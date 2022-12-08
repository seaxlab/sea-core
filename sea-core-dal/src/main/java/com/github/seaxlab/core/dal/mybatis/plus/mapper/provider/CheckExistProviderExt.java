package com.github.seaxlab.core.dal.mybatis.plus.mapper.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.github.seaxlab.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.property.PropertyNamer;

/**
 * 如果只判断是否存在只要一条即可 select 1 from table where column1='xx' limit 1
 *
 * @author spy
 * @version 1.0 2021/6/13
 * @since 1.0
 */
@Slf4j
public class CheckExistProviderExt {

  // select count(*)>0 from sys_user where corp_code='3702011121' limit 1;


  public String checkExistByFun1(SFunction<?, ?> column) {
    String format = "SELECT count(*)>0 FROM %s WHERE %s = #{val} limit 1";

    return oneField(format, column);
  }

  public String checkExistByFun2(SFunction<?, ?> column1, SFunction<?, ?> column2) {
    String format = "SELECT count(*)>0 FROM %s WHERE %s = #{val1} and %s = #{val1} limit 1";

    return twoField(format, column1, column2);
  }

  public String checkExistByWrapper(QueryWrapper wrapper) {
    Precondition.checkNotNull(wrapper);

    String format = "SELECT count(*)>0 FROM %s WHERE %s limit 1";
    String tableName = TableInfoHelper.getTableInfo(wrapper.getEntityClass()).getTableName();

    return String.format(format, tableName, wrapper.getSqlSegment());
  }


  //------------------
  private String oneField(String format, SFunction<?, ?> column) {
    SerializedLambda lambda = LambdaUtils.resolve(column);
    Class<?> clazz = lambda.getInstantiatedType();
    String tableName = TableInfoHelper.getTableInfo(clazz).getTableName();
    String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
    return String.format(format, tableName, fieldName);
  }

  private String twoField(String format, SFunction<?, ?> column1, SFunction<?, ?> column2) {
    SerializedLambda lambda1 = LambdaUtils.resolve(column1);
    Class<?> clazz = lambda1.getInstantiatedType();
    String tableName = TableInfoHelper.getTableInfo(clazz).getTableName();
    String fieldName1 = PropertyNamer.methodToProperty(lambda1.getImplMethodName());
    SerializedLambda lambda2 = LambdaUtils.resolve(column2);
    String fieldName2 = PropertyNamer.methodToProperty(lambda2.getImplMethodName());
    return String.format(format, tableName, fieldName1, fieldName2);
  }

}
