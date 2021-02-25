package com.github.spy.sea.core.io.archiver;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/18
 * @since 1.0
 */
@Slf4j
public class PlexusZipArchiverTest extends BaseCoreTest {

    // plexus zip archiver
    @Test
    public void archiverTest() throws Exception {
        String sourceDir = getUserHome() + "/logs/sea";
        String destFile = getUserHome() + "/logs/sea.zip";

        FileUtil.deleteFiles(destFile);

        Archiver archiver = new ZipArchiver();

        archiver.addDirectory(new File(sourceDir));
        archiver.setDestFile(new File(destFile));

        archiver.createArchive();

        log.info("zip file create={}", FileUtil.exist(destFile));
    }

    @Test
    public void unArchiverTest() throws Exception {

        String sourceFile = getUserHome() + "/logs/sea.zip";
        String destDir = getUserHome() + "/logs/sea2";
        FileUtil.ensureDir(destDir);

        ZipUnArchiver unArchiver = new ZipUnArchiver(new File(sourceFile));
        unArchiver.enableLogging(new ConsoleLogger(org.codehaus.plexus.logging.Logger.LEVEL_INFO, "console"));
        unArchiver.setDestDirectory(new File(destDir));
        unArchiver.extract();
    }

}
