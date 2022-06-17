package com.github.seaxlab.core.support.oss.dto;

import com.github.seaxlab.core.support.oss.enums.AclEnum;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/17
 * @since 1.0
 */
@Data
public class BucketCreateDTO {
    private String name;
    private AclEnum aclEnum;
}
