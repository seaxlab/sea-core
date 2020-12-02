package com.github.spy.sea.core.dal.jdbc;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.*;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/2
 * @since 1.0
 */
@Slf4j
public class JdbcTest {

    Connection conn;

    @Test
    public void queryTest() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("select * from student");
        ResultSet rs = pstmt.executeQuery();
        try {
            while (rs.next()) {
                System.out.println(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertTest() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("insert into student1 values (?,?,?,?)");
        pstmt.setString(1, "Name");
        pstmt.setString(2, "Grade");
        pstmt.setString(3, "Sex");
        pstmt.setString(4, "Sno");
        int numInsert = pstmt.executeUpdate(); //返回插入的数量
    }


    @Test
    public void updateTest() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("update student1 set NAME=?,SEX=?,DEPT=? where NO=?");
        pstmt.setString(1, "Name");
        pstmt.setString(2, "Grade");
        pstmt.setString(3, "Sex");
        pstmt.setString(4, "Sno");
        int numDelete = pstmt.executeUpdate();
    }

    @Test
    public void deleteTest() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("delete from student1 where NO=?");
        pstmt.setString(1, "Sno");
        int numDelete = pstmt.executeUpdate();
    }


    @Test
    public void batch1Test() throws Exception {
        Statement statement = conn.createStatement();

        statement.addBatch("update people set firstname='John' where id=123");
        statement.addBatch("update people set firstname='Eric' where id=456");
        statement.addBatch("update people set firstname='May'  where id=789");

        int[] recordsAffected = statement.executeBatch();
    }

    @Test
    public void batch2Test() throws Exception {
        String sql = "update people set firstname=? , lastname=? where id=?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, "Gary");
            preparedStatement.setString(2, "Larson");
            preparedStatement.setLong(3, 123);

            preparedStatement.addBatch();

            preparedStatement.setString(1, "Stan");
            preparedStatement.setString(2, "Lee");
            preparedStatement.setLong(3, 456);

            preparedStatement.addBatch();

            int[] affectedRecords = preparedStatement.executeBatch();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    public void batch3Test() throws Exception {

        List<Person> persons = Lists.newArrayList();


        String sql = "update people set firstname=? , lastname=? where id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);

            for (Person person : persons) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setLong(3, person.getId());

                preparedStatement.addBatch();
            }

            int[] affectedRecords = preparedStatement.executeBatch();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Data
    public static final class Person {
        private Long id;
        private String firstName;
        private String lastName;
    }

}
