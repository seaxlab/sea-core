package com.github.seaxlab.core.component.buffertrigger.simple;

import java.util.concurrent.TimeUnit;
import lombok.Getter;

/**
 * 触发器执行结果，计划任务内部使用，一般无需关注
 */
@Getter
public class TriggerResult {

  private static final TriggerResult EMPTY = new TriggerResult(false, TimeUnit.DAYS.toMillis(1));
  private final boolean consumerFlag;
  private final long nextPeriod;

  private TriggerResult(boolean consumerFlag, long nextPeriod) {
    this.consumerFlag = consumerFlag;
    this.nextPeriod = nextPeriod;
  }

  public static TriggerResult trig(boolean doConsumer, long nextPeriod) {
    return new TriggerResult(doConsumer, nextPeriod);
  }

  public static TriggerResult empty() {
    return EMPTY;
  }
}
