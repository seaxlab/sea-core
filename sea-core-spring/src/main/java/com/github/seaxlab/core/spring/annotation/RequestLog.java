package com.github.seaxlab.core.spring.annotation;

import java.lang.annotation.*;

/**
 * print request log.
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @see org.springframework.web.filter.CommonsRequestLoggingFilter
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestLog {

    /**
     * show query string
     *
     * @return
     */
    boolean queryString() default true;

    /**
     * show client info
     *
     * @return
     */
    boolean clientInfo() default true;

    /**
     * show header info
     *
     * @return
     */
    boolean header() default true;

    /**
     * show payload
     *
     * @return
     */
    boolean payload() default true;

    /**
     * max payload length
     *
     * @return
     */
    int maxPayloadLength() default 100;


    /**
     * show response, default false;
     *
     * @return
     */
    boolean response() default false;

    /**
     * max response length
     * <p>
     * if over this value, not print response info
     * </p>
     *
     * @return
     */
    int maxResponseLength() default 100;

}
