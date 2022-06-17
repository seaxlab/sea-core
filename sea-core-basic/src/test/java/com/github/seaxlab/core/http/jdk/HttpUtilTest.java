package com.github.seaxlab.core.http.jdk;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/26
 * @since 1.0
 */
@Slf4j
public class HttpUtilTest extends BaseCoreTest {

    @Test
    public void testSupportRange() throws Exception {
        String url = "https://www.baidu.com/s?wd=%E7%99%BE%E5%BA%A6%E7%83%AD%E6%90%9C&sa=ire_dl_gh_logo_texing&rsv_dl=igh_logo_pc";
        log.info("support range={}", HttpUtil.isSupportRange(url));
        // 15KB
        log.info("file size={}byte", HttpUtil.getFileContentLength(url));
    }
}
