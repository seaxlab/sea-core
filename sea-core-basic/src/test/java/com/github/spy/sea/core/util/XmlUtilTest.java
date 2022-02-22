package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.junit.Test;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/30
 * @since 1.0
 */
@Slf4j
public class XmlUtilTest extends BaseCoreTest {

    @Test
    public void testToXmlString() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Lokesh");
        employee.setLastName("Gupta");
        employee.setDepartment(new Department(101, "IT"));
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "1"));
        departments.add(new Department(2, "2"));
        employee.setDepartments(departments);

        System.out.println(XmlUtil.toString(employee));
    }

    @Test
    public void testParse() throws Exception {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<employee>\n" +
                "    <department>\n" +
                "        <id>101</id>\n" +
                "        <name>IT</name>\n" +
                "    </department>\n" +
                "    <firstName>Lokesh</firstName>\n" +
                "    <id>1</id>\n" +
                "    <lastName>Gupta</lastName>\n" +
                "</employee>";

        Employee employee = XmlUtil.parse(xml, Employee.class);
        log.info("{}", employee);

    }


    @Test
    public void normalTest() throws Exception {
        String url = PathUtil.getPathFromClassPath("demo-config.xml");
        Element el = XmlUtil.getRoot(url);

        DemoConfig config = XmlUtil.getBean(el, DemoConfig.class);
        log.info("{}", config);
    }

    @Test
    public void notExistFieldTest() throws Exception {
        String url = PathUtil.getPathFromClassPath("demo-config.xml");
        Element el = XmlUtil.getRoot(url);

        DemoConfig2 config = XmlUtil.getBean(el, DemoConfig2.class);
        log.info("config={}", config);
    }

    @Data
    public static class DemoConfig {
//        private List<User> users;
//        private Config config;
//        private String content;
//        private Integer count;
//                 not exist element
//        private String id;
//        private Integer money;
//        private List<Boolean> keys;

        private List<String> ids = new ArrayList<>();
    }

    @Data
    public static class DemoConfig2 {
        private String id;
        private Integer money;
        private Card card;
    }


    @Data
    public static class User {
        private String id;
        private String name;
    }

    @Data
    public static class Config {
        private Boolean open;
        private Boolean close;
    }

    @Data
    public static class Card {
        private String id;
    }

    @XmlRootElement(name = "employee")
    @XmlAccessorType(XmlAccessType.PROPERTY)
    @Data
    public static class Employee implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer id;
        private String firstName;
        private String lastName;
        private Department department;


        private List<Department> departments;

        @XmlElement(name = "department", type = Department.class)
        @XmlElementWrapper(name = "departments")
        public List<Department> getDepartments() {
            return departments;
        }

        public Employee() {
            super();
        }

        //Setters and Getters

        // Invoked by Marshaller after it has created an instance of this object.
        boolean beforeMarshal(Marshaller marshaller) {
            System.out.println("Before Marshaller Callback");
            return true;
        }

        // Invoked by Marshaller after it has marshalled all properties of this object.
        void afterMarshal(Marshaller marshaller) {
            System.out.println("After Marshaller Callback");
        }
    }

    @Data
    //@XmlRootElement
    public static class Department {
        private Integer id;
        private String name;

        public Department() {
        }

        public Department(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
