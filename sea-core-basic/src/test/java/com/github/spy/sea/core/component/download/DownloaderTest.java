package com.github.spy.sea.core.component.download;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.component.download.impl.MultiThreadDownloader;
import com.github.spy.sea.core.component.download.impl.SimpleDownloader;
import com.github.spy.sea.core.component.download.model.DownloaderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Slf4j
public class DownloaderTest extends BaseCoreTest {

    String file1 = "https://download.jetbrains.8686c.com/idea/ideaIU-2020.3.dmg";
    String file2 = "https://downloads.gradle-dn.com/distributions/gradle-6.7.1-bin.zip";

    @Before
    public void before() throws Exception {
        super.before();
    }

    @Test
    public void simpleDownloaderTest() throws Exception {
        DownloaderDTO dto = new DownloaderDTO();
        dto.setRemoteFileUrl(file2);
        dto.setNewDir(getUserHome() + "/sea");
        dto.setNewFileName("download-simple.zip");
        dto.setOverwrite(true);

        Downloader downloader = new SimpleDownloader();
        downloader.execute(dto);
    }

    @Test
    public void multiThreadDownloadTest() throws Exception {

        DownloaderDTO dto = new DownloaderDTO();
        dto.setRemoteFileUrl(file2);
        dto.setNewDir(getUserHome() + "/sea");
        dto.setNewFileName("download-bigfile.zip");
        dto.setOverwrite(true);

        Downloader downloader = new MultiThreadDownloader();
        downloader.execute(dto);
    }
}
