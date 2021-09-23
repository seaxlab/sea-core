package com.github.spy.sea.core.dal.screw;

import com.github.spy.sea.core.dal.BaseCoreDalTest;
import com.github.spy.sea.core.util.IdUtil;
import com.github.spy.sea.core.util.PathUtil;
import com.github.spy.sea.core.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/25
 * @since 1.0
 */
@Slf4j
public class ScrewTest extends BaseCoreDalTest {

    protected String getFileName(String prefix) {
        return prefix + "_" + IdUtil.getYYYYMMDDHHMM();
    }

    public String getPassword(String key) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(PathUtil.getSeaHome(), "sea.password.properties"));

            Properties properties = PropertiesUtil.load(fileInputStream);
            String value = properties.getProperty(key, "");
            log.info("get properties {}={}", key, value);
            return value;
        } catch (Exception e) {
            log.error("", e);
        }

        return "";
    }

}
