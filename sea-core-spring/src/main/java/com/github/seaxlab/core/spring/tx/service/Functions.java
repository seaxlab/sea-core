package com.github.seaxlab.core.spring.tx.service;

public class Functions {

  @FunctionalInterface
  public interface Func0<R> {

    R apply();
  }

  @FunctionalInterface
  public interface Func1<T1, R> {

    R apply(T1 t1);
  }

  @FunctionalInterface
  public interface Func2<T1, T2, R> {

    R apply(T1 t1, T2 t2);
  }

  @FunctionalInterface
  public interface Func3<T1, T2, T3, R> {

    R apply(T1 t1, T2 t2, T3 t3);
  }
}
