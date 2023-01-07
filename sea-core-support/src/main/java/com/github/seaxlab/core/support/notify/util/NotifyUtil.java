package com.github.seaxlab.core.support.notify.util;

import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.support.notify.dto.BaseNotifyDTO;
import com.github.seaxlab.core.util.ObjectUtil;
import com.github.seaxlab.core.util.PlaceholderUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/30
 * @since 1.0
 */
@Slf4j
public final class NotifyUtil {

  private NotifyUtil() {
  }

  public static String getContent(BaseNotifyDTO dto) {
    Precondition.checkNotNull(dto);

    if (StringUtil.isNotBlank(dto.getContent())) {
      return dto.getContent();
    }

    if (ObjectUtil.isNotEmpty(dto.getTemplate(), dto.getParam())) {
      return PlaceholderUtil.INSTANCE.replace(dto.getTemplate(), dto.getParam());
    }

    log.warn("can not determine message content.");
    return StringUtil.empty();
  }

}
