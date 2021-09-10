package com.github.spy.sea.core.dal.screw;

import com.github.spy.sea.core.dal.BaseCoreDalTest;
import com.github.spy.sea.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

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

}
