package com.github.seaxlab.core.model.component.person;

import com.github.seaxlab.core.model.DTO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/9
 * @since 1.0
 */
@Data
public class Address extends DTO {

    // 省
    private String provinceCode;
    private String provinceName;

    // 市
    private String cityCode;
    private String cityName;

    // 区
    private String countryCode;
    private String countryName;

    // 街道
    private String streetCode;
    private String streetName;

    // 详细地址，门牌
    private String detail;

    // 邮政编码
    private String postCode;

    // 用户名称
    private String userName;

    // 用户手机号
    private String phone;

    // 是否默认
    private Boolean isDefault;

    /**
     * 全名
     *
     * @return string
     */
    public String getFullAddress() {
        String address = contact(provinceName)
                + contact(cityName, "")
                + contact(countryName, "")
                + contact(streetName, "")
                + contact(detail, "");

        return address.trim();
    }

    private String contact(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        return name;
    }

    private String contact(String name, String defaultVal) {
        if (name == null || name.trim().isEmpty()) {
            return defaultVal;
        }
        return name + " ";
    }

}
