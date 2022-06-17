package com.github.seaxlab.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * external param, for example: addUserCmd/OrderPlaceCmd
 *
 * @author spy
 * @version 1.0 2021/7/24
 * @since 1.0
 */
@Data
public class BaseCmd implements Serializable {

    private String requestId;
}
