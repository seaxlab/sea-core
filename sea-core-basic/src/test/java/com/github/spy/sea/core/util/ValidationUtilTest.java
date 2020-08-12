package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/12
 * @since 1.0
 */
@Slf4j
public class ValidationUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);

        Set<ConstraintViolation<BookDTO>> violations = ValidationUtil.getValidator().validate(bookDTO);
        if (violations.size() > 0) {
            log.error("has error,{}", violations);
        }

    }

    @Data
    public static class BookDTO {

        @NotNull
        @Range(min = 6, max = 10)
        private Long id;

        @NotNull(message = "书名不能为空!")
        private String name;

    }
}
