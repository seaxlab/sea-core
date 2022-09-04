package com.github.seaxlab.core.spring.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.seaxlab.core.model.layer.po.EntityKey;
import lombok.Data;

/**
 * User POJO
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 2017.03.31
 */
@Data
public class User implements EntityKey {

    private String code;
    private String name;

    private int age;

    @Override
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public String getEntityKey() {
        return code;
    }
}
