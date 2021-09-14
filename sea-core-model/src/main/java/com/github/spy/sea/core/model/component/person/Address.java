package com.github.spy.sea.core.model.component.person;

import com.github.spy.sea.core.model.DTO;
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

    // 具体地址
    private String detail;

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
