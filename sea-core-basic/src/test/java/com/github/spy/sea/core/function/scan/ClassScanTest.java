package com.github.spy.sea.core.function.scan;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.function.scan.impl.DefaultClassScan;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/13
 * @since 1.0
 */
@Slf4j
public class ClassScanTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        ClassScan scanner = new DefaultClassScan();

        Set<String> classSet = scanner.get("com.github.spy.sea.core.function");

        classSet.stream().forEach(item -> log.info("{}", item));
    }

    @Test
    public void test31() throws Exception {
        ClassScan scanner = new DefaultClassScan();

        List<String> packages = new ArrayList<>();
        packages.add("com.github.spy.sea.core.function");
        Set<String> classSet = scanner.get(packages);

        classSet.stream().forEach(item -> log.info("{}", item));
    }

    @Test
    public void test44() throws Exception {
        ClassScan scanner = new DefaultClassScan();

        List<String> packages = new ArrayList<>();
        packages.add("com.github.spy.sea.core.function");
        Set<Class<?>> classSet = scanner.get(packages, Beta.class);

        classSet.stream().forEach(item -> log.info("{}", item));
    }
}
