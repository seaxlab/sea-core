package com.github.seaxlab.core.component.sort;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.sort.impl.InsertSort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class SortTest extends BaseCoreTest {

    @Test
    public void testInsertSort() throws Exception {
        int[] array = {4, 3, 60, 50, 20};

        Sort sort = new InsertSort();
        sort.sort(array);
        log.info("after sort: {}", array);
    }
}
