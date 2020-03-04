package com.github.spy.sea.core.support.notify.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */

@Data
public class BaseNotifyDTO extends BaseRequestDTO {

    private String title;

    private String content;
}
