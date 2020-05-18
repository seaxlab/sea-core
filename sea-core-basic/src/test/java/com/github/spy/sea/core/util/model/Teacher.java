package com.github.spy.sea.core.util.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/18
 * @since 1.0
 */
@Data
public class Teacher implements Serializable {
    private String name;
    private List<Student> students;
}
