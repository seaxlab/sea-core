package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/16
 * @since 1.0
 */
@Slf4j
public class RegExpUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        log.info("{}", RegExpUtil.isCarNumber("浙A10232"));
    }

    @Test
    public void run23() throws Exception {
        //不含加号
        println(RegExpUtil.is("a_", RegExpUtil.letter_number_underline_regexp)); //true
        println(RegExpUtil.is("a-", RegExpUtil.letter_number_underline_regexp)); //false
        println(RegExpUtil.is("a.", RegExpUtil.letter_number_underline_regexp)); //false
        println(RegExpUtil.is("a+", RegExpUtil.letter_number_underline_regexp)); //false
    }

    @Test
    public void run32() throws Exception {
        println(RegExpUtil.is("Aa1-_.", RegExpUtil.normal_id_regexp));
    }

    @Test
    public void testChinese() throws Exception {
        println(RegExpUtil.find("中文", RegExpUtil.REGEX_CHINESE));
        println(RegExpUtil.find("12abc中文", RegExpUtil.REGEX_CHINESE));

    }
}
