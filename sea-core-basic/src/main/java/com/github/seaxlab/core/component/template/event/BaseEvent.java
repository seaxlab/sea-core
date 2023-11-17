package com.github.seaxlab.core.component.template.event;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * base event
 *
 * @author spy
 * @version 1.0 2023/11/17
 * @since 1.0
 */
@Data
public class BaseEvent implements Serializable {

  /**
   * 业务类型,推荐100已上
   */
  private Integer type = 0;

  /**
   * 消息体
   */
  private String payload;

  /**
   * 扩展信息
   */
  private String extra;

  /**
   * 事件来源
   */
  private String source;

  /**
   * 事件创建时间
   */
  private Date createTime;

  /**
   * 时间创建人ID
   */
  private String createUserId;

  /**
   * 时间创建人名称
   */
  private String createUserName;

  /**
   * 事件版本，默认1
   */
  private Integer version;
}
