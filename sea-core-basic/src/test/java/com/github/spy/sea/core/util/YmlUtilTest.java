package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/14
 * @since 1.0
 */
@Slf4j
public class YmlUtilTest extends BaseCoreTest {

    @Test
    public void loadTest() throws Exception {
        Customer customer = YmlUtil.load(Customer.class, getInputStream("yaml/test.yaml"));
        log.info("customer={}", customer);
    }

    @Test
    public void loadAllTest() throws Exception {
//        Constructor constructor = new Constructor(Customer.class);
//        TypeDescription customTypeDescription = new TypeDescription(Customer.class);
//        customTypeDescription.addPropertyParameters("contactDetails", Contact.class);
//        constructor.addTypeDescription(customTypeDescription);
//        Yaml yaml = new Yaml(constructor);

        List<Customer> customers = YmlUtil.loadAll(Customer.class, getInputStream("yaml/test-multi.yaml"));
        log.info("customer={}", customers);
        customers.forEach(customer -> {
            log.info("customer={}", customer);
        });
    }

    @Test
    public void loadAll2Test() throws Exception {
        List<Customer> customers = YmlUtil.loadAll(Customer.class, getInputStream("yaml/test-multi-empty.yaml"));
        log.info("customer={}", customers);
        customers.forEach(customer -> {
            log.info("customer={}", customer);
        });
    }

    @Test
    public void dumpTest() throws Exception {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("name", "Silenthand Olleander");
        data.put("race", "Human");
        data.put("traits", new String[]{"ONE_HAND", "ONE_EYE"});
        log.info("dump str={}", YmlUtil.dump(data));
    }


    @Data
    public static class Customer {
        private String firstName;
        private String lastName;
        private int age;
        private List<Contact> contactDetails;
        private Address homeAddress;
        // getters and setters
    }

    @Data
    public static class Contact {
        private String type;
        private int number;
        // getters and setters
    }

    @Data
    public static class Address {
        private String line;
        private String city;
        private String state;
        private Integer zip;
        // getters and setters
    }
}
