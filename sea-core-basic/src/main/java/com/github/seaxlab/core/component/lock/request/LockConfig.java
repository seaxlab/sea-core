package com.github.seaxlab.core.component.lock.request;

import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.Data;

import java.util.Collection;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
@Data
public class LockConfig {
  private String bizName;
  //
  private String lockKey;
  private Collection<String> lockKeys;
  //
  private boolean throwOnFailFlag;


  public String getLockKeyString() {
    if (StringUtil.isNotBlank(lockKey)) {
      return lockKey;
    }

    if (CollectionUtil.isNotEmpty(lockKeys)) {
      return String.join(",", lockKeys);
    }

    return "";
  }
}
