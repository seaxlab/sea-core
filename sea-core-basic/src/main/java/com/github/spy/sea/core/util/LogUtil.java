package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/26
 * @since 1.0
 */
@Slf4j
public final class LogUtil {

    /**
     * sys.out.print table.
     *
     * @param headers headers
     * @param data    data
     */
    public static void printTable(List<String> headers, List data) {
        printTable(headers, data, 16);
    }

    /**
     * sys.out.print table.
     *
     * @param headers     table header
     * @param data        data
     * @param columnWidth column width
     */
    public static void printTable(List<String> headers, List data, int columnWidth) {
        String format = "%" + columnWidth + "s|";
        for (String header : headers) {
            System.out.printf(format, header);
        }
        System.out.println("");
        for (String header : headers) {
            System.out.print(StringUtils.leftPad("|", columnWidth + 1, "_"));
        }
        System.out.println("");

        if (data == null || data.isEmpty()) {
            return;
        }

        data.stream().forEach(item -> {
            headers.stream().forEach(header -> {
                Object value = null;
                try {
                    value = FieldUtils.readField(item, header, true);
                } catch (Exception e) {
                    log.error("fail to read field.", e);
                    value = "";
                }
                System.out.printf(format, value);
            });
            System.out.println("");
        });

    }
}
