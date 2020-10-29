package com.github.spy.sea.core.domain;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/29
 * @since 1.0
 */
@Data
public class User {

    private Long id;
    private String name;
    List<Role> roles;

    private boolean isSuc;
    private Boolean isUsed;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User user = (User) o;
//        return isSuc() == user.isSuc() &&
//                Objects.equals(getId(), user.getId()) &&
//                Objects.equals(getName(), user.getName()) &&
//                Objects.equals(getRoles(), user.getRoles()) &&
//                Objects.equals(getIsUsed(), user.getIsUsed());
//    }

//    @Override
//    public int hashCode() {
//    return 1;
//    }
}
