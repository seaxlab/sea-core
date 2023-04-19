package com.github.seaxlab.core.component.lock.request;

import lombok.Data;
import lombok.ToString;

/**
 * lock request
 *
 * @author spy
 * @version 1.0 2023/04/19
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class LockRequest extends BaseLockRequest {

  private Runnable runnable;

}
