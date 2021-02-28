package com.github.spy.sea.core.component.dag;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 基于Guava的相互依赖的线程执行实现
 * 简单的有向无环图，DAG
 * <pre>
 *       --B--C-----
 *  A --            ---E
 *       --D ------
 * </pre>
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class SimpleDAG<T> {

    private ListeningExecutorService executor;
    private Map<String, ListenableFuture<T>> futureMap;

    public SimpleDAG() {
        this(Runtime.getRuntime().availableProcessors() + 1);
    }

    public SimpleDAG(int poolSize) {
        executor = MoreExecutors.listeningDecorator(MoreExecutors.getExitingExecutorService(
                (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize)));

        futureMap = new HashMap<>();
    }


    /**
     * 添加依赖
     *
     * @param name
     * @param callable
     * @param predecessorNames
     * @return
     */
    public Future<T> addTask(String name, final Callable<T> callable, String... predecessorNames) {

        if (futureMap.containsKey(name)) {
            throw new IllegalArgumentException("Task name exists.");
        }

        List<ListenableFuture<T>> predecessorFutures = new ArrayList<>();
        for (String predecessorName : predecessorNames) {
            ListenableFuture<T> predecessorFuture = futureMap.get(predecessorName);
            if (predecessorFuture == null) {
                throw new IllegalArgumentException("Predecessor task doesn't exist.");
            }
            predecessorFutures.add(predecessorFuture);
        }

        ListenableFuture<T> future;
        if (predecessorFutures.isEmpty()) {
            future = executor.submit(callable);
        } else {
            future = Futures.transformAsync(Futures.allAsList(predecessorFutures), new AsyncFunction<List<T>, T>() {

                @Override
                public ListenableFuture<T> apply(List<T> input) throws Exception {
                    return executor.submit(callable);
                }

            }, executor);
        }
        futureMap.put(name, future);

        return future;
    }

    /**
     * 关闭线程释放资源
     */
    public void shutdown() {
        executor.shutdown();
        futureMap = null;
    }
}
