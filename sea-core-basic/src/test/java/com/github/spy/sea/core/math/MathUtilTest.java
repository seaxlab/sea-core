package com.github.spy.sea.core.math;

import cn.hutool.core.math.Combination;
import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public class MathUtilTest extends BaseCoreTest {

    String[] data = new String[]{"1", "2", "3", "4", "5"};

    @Test
    public void run44() throws Exception {
        List<String[]> list = new Combination(data).select(2);

        list.stream().forEach(item -> {
            String str = StringUtil.join(",", item);
            log.info("{}", str);
        });
    }

    @Test
    public void allSelectTest() throws Exception {
        //
        for (int i = 0; i < data.length; i++) {
            log.info("选择{}个", i + 1);
            List<String[]> list = new Combination(data).select(i + 1);
            list.stream().forEach(item -> {
                String str = StringUtil.join(",", item);
                log.info("{}", str);
            });
            log.info("-------------------");
        }
    }

    @Test
    public void run74() throws Exception {
        // 全组合
        List<String[]> list = MathUtil.combinationSelectAll(data);
        list.stream().forEach(item -> {
            String str = StringUtil.join(",", item);
            log.info("{}", str);
        });
    }

    @Test
    public void run58() throws Exception {
        List<String[]> list = MathUtil.combinationSelectAllNoOrder(data);
        list.stream().forEach(item -> {
            String str = StringUtil.join(",", item);
            log.info("{}", str);
        });
    }

    @Test
    public void run67() throws Exception {
        sort(new String[]{"1"});
        sort(new String[]{"1", "2"});
        sort(new String[]{"1", "2", "3"});
    }

    private static void sort(String[] data) {
        List<String[]> list = MathUtil.combinationSelectAllNoOrder(data);
        list.stream().forEach(item -> {
            String str = StringUtil.join(",", item);
            log.info("{}", str);
        });
        log.info("-------");
    }
}
