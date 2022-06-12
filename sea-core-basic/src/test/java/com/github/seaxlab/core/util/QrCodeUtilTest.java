package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/26
 * @since 1.0
 */
@Slf4j
public class QrCodeUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        File file = new File(getUserHome() + "/sea/core/qr-code.png");
        FileUtil.ensureDir(file.getParent());

        QRCodeUtil.encode(file.getPath(), "abc");

        String content = QRCodeUtil.decode(file.getPath());

        Assert.assertEquals("abc", content);
    }
}
