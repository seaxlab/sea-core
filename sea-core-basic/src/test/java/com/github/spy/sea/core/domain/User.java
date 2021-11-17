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
    private int age;

    List<Role> roles;
    List<String> roleCodes;

    private boolean isSuc;
    private Boolean isUsed;

    private Group group;

    private String remark;

    private String remark2 = "abc";

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
