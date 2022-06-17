package com.github.seaxlab.core.test;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/16
 * @since 1.0
 */
@Slf4j
@BenchmarkMode(Mode.Throughput)
public class SimpleJmhTest {

    // 只能run运行，不能有@Test注解

    @Benchmark
    @Warmup(iterations = 1, time = 2)
    @Threads(2)
    @Fork(2)
    @Measurement(iterations = 2, time = 2)
    public void testForEach() {
        NumsSum.foreach();
    }


    @BenchmarkMode(Mode.AverageTime) // 指定mode为Mode.AverageTime
    @Benchmark
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    public void testGson() {
        new Gson().fromJson("{\"startDate\":\"2020-04-01 16:00:00\",\"endDate\":\"2020-05-20 13:00:00\",\"flag\":true,\"threads\":5,\"shardingIndex\":0}", UserInfo.class);
    }


}
