package com.github.seaxlab.core.support.notify.dto;

import com.github.seaxlab.core.model.layer.dto.BaseRequestDTO;
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
