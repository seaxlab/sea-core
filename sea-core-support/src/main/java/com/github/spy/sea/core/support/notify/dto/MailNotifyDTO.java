package com.github.spy.sea.core.support.notify.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */
@Data
public class MailNotifyDTO extends BaseNotifyDTO {

    private MailServerConfigDTO mailServerConfigDTO;

    /**
     * 收件人
     */
    private String to;

    /**
     * 抄送人
     */
    private String cc;

    /**
     * 密送
     */
    private String bcc;


    private Boolean isHtml;
}
