package com.github.seaxlab.core.http.dto;

import com.github.seaxlab.core.model.layer.dto.BaseRequestDTO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/29
 * @since 1.0
 */

@Data
public class BaseHttpDTO extends BaseRequestDTO {
    private String url;
}
