package com.github.seaxlab.core.component.buffertrigger.common;

import com.github.seaxlab.core.component.buffertrigger.BufferTrigger;
import com.github.seaxlab.core.component.buffertrigger.util.MoreSuppliers;
import com.github.seaxlab.core.component.buffertrigger.util.MoreSuppliers.CloseableSupplier;
import java.util.function.Supplier;

/**
 * @author w.vela
 */
public class LazyBufferTrigger<E> implements BufferTrigger<E> {

  private final CloseableSupplier<BufferTrigger<E>> factory;

  public LazyBufferTrigger(Supplier<BufferTrigger<E>> factory) {
    this.factory = MoreSuppliers.lazy(factory);
  }

  @Override
  public void enqueue(E element) {
    this.factory.get().enqueue(element);
  }

  @Override
  public void manuallyDoTrigger() {
    this.factory.ifPresent(BufferTrigger::manuallyDoTrigger);
  }

  @Override
  public long getPendingChanges() {
    return this.factory.map(BufferTrigger::getPendingChanges).orElse(0L);
  }

  @Override
  public void close() {
    this.factory.ifPresent(BufferTrigger::close);
  }
}
