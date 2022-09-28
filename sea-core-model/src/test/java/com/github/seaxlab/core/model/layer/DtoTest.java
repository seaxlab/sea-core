package com.github.seaxlab.core.model.layer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/29
 * @since 1.0
 */
@Slf4j
public class DtoTest {

    @Test
    public void test17() throws Exception {
        AddDTO dto = new AddDTO();
        dto.getExtend();
    }

    @Data
    public static class AddDTO {
        // only get method if set final here.
        private final Extend extend = new Extend();

        @Data
        public static class Extend {
        }
    }
}
