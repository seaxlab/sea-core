package com.github.seaxlab.core.support.ditu.gaode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * input tips request dto
 *
 * @author spy
 * @version 1.0 2023/8/26
 * @since 1.0
 */
@Data
@JsonInclude(Include.NON_NULL)
public class InputTipsReqDTO extends BaseGaoDeReqDTO {

  private String keywords;
}
