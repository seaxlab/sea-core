package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class FileUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String txt = FileUtil.readFormClasspath("util/users.json");

        log.info("txt={}", txt);
    }

    @Test
    public void listFileTest() throws Exception {
        File[] files = FileUtil.listFiles(new File(getUserHome() + "/logs"), new String[]{".log"});
        for (int i = 0; i < files.length; i++) {
            log.info("files={}", files[i]);
        }
    }

    @Test
    public void test35() throws Exception {

        String logDir = getUserHome() + "/logs";
        long size = FileUtil.sizeOfDir(logDir);
        log.info("size={},human size={}", size, ByteUnitUtil.format(size));
    }
}
