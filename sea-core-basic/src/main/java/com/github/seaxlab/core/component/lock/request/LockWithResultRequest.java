package com.github.seaxlab.core.component.lock.request;

import lombok.Data;
import lombok.ToString;

import java.util.function.Supplier;

/**
 * lock with result request
 *
 * @author spy
 * @version 1.0 2023/04/19
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class LockWithResultRequest<R> extends BaseLockRequest {

  private Supplier<R> supplier;

}
