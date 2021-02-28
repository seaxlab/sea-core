package com.github.spy.sea.core.component.dag;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class SimpleDAGTest {

    @Test
    public void run18() throws Exception {
        SimpleDAG<Void> pool = new SimpleDAG();
        pool.addTask("A", new SqlTask("A"));
        pool.addTask("B", new SqlTask("B"), "A");
        pool.addTask("C", new SqlTask("C"));
        pool.addTask("D", new SqlTask("D"), "B", "C").get();

        pool.shutdown();
    }

    public class SqlTask implements Callable<Void> {

        private String sql;

        public SqlTask(String sql) {
            this.sql = sql;
        }

        @Override
        public Void call() throws Exception {
            log.debug("Processing sql={}", sql);

            Thread.sleep(2000);
            return null;
        }

    }
}
