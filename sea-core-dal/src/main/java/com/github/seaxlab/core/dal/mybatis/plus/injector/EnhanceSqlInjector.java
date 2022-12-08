package com.github.seaxlab.core.dal.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.github.seaxlab.core.dal.mybatis.plus.method.CheckExist;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/7
 * @since 1.0
 */
@Slf4j
public class EnhanceSqlInjector extends DefaultSqlInjector {

  /**
   * 如果只需增加方法，保留MP自带方法 可以super.getMethodList() 再add
   *
   * @return
   */
  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);
    methodList.add(new CheckExist());
    return methodList;
  }

}
