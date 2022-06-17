package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
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

        println(RegExpUtil.find("國", RegExpUtil.REGEX_CHINESE)); // true
        println(RegExpUtil.find("國", RegExpUtil.REGEX_CHINESE_COMPLEX)); // false, why false
    }

    @Test
    public void testGetByBracket1() throws Exception {
        println(RegExpUtil.getByBracket("a(abcd)d(aa)"));

    }

    @Test
    public void testGetByBracket2() throws Exception {
        println(RegExpUtil.getByBracket("a(abcd)", "a"));
    }
}
