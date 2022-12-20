package com.github.seaxlab.core.component.buffertrigger.simple;


import com.github.seaxlab.core.component.buffertrigger.BufferTrigger;
import javax.annotation.Nullable;

/**
 * @author w.vela Created on 2021-02-04.
 */
public interface NameRegistry {

  /**
   * 注意，当前还只支持 {@link BufferTrigger#simple()} 方式构建的命名获取
   */
//  static NameRegistry autoRegistry() {
//    return () -> {
//      StackTraceElement callerPlace = MoreReflection.getCallerPlace(
//        SimpleBufferTriggerBuilder.class);
//      if (callerPlace != null && !StringUtils.equals("ReflectionUtils.java",
//        callerPlace.getFileName())) {
//        return callerPlace.getFileName() + ":" + callerPlace.getLineNumber();
//      } else {
//        return null;
//      }
//    };
//  }
  @Nullable
  String name();
}
