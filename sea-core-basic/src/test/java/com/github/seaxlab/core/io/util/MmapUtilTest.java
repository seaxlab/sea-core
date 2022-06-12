package com.github.seaxlab.core.io.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/10/28
 * @since 1.0
 */
@Slf4j
public class MmapUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        File data = new File("/tmp/data_mmap_test.txt");
        if (data.exists()) {
            data.delete();
        }
        data.createNewFile();
        FileChannel fileChannel = new RandomAccessFile(data, "rw").getChannel();
        // 1M
        // maybe change to 1024L*1024*1024（1G）
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024);

        TimeUnit.SECONDS.sleep(10);

        log.info("after 10s...");

        MmapUtil.clean(map);
        new CountDownLatch(1).await();
    }
}
