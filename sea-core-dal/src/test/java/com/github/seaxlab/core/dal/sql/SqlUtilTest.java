package com.github.seaxlab.core.dal.sql;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/12/1
 * @since 1.0
 */
@Slf4j
public class SqlUtilTest {

  @Test
  public void testSelectAll() throws Exception {
    String sql = "SELECT t.*\n"
      + " FROM t_xx AS t \n"
      + "WHERE (t.id = ? AND t.object_id = ? AND t.deleted = ?) \n"
      + "LIMIT 0, 1 \n"
      + " ARGS: [1, 10000, 0], COST:2 ms ";

    System.out.println(sql);
    System.out.println(SqlUtil.parse(sql));
  }


  @Test
  public void testSelectCount() throws Exception {
    String sql = "SELECT \n"
      + "  COUNT(*) AS  TOTAL \n"
      + " FROM t_xx AS t_xx \n"
      + "WHERE (((t_xx.code = ? AND t_xx.model = ? AND t_xx.category = ?) AND t_xx.deleted = ? AND t_xx.object_id = ? AND t_xx.tenant_id = ?)) \n"
      + "LIMIT 0, 10 \n"
      + " ARGS: [ Fri Dec 01 14:26:45 CST 2023, 111.2G50E317A(DH3.2G0027*01), SR1623, 0, 1, 0], COST:1 ms ";
    System.out.println(SqlUtil.parseQuery(sql));
  }

  @Test
  public void testSelect() throws Exception {
    String sql =
      "SELECT t.*\n"
        + " FROM t_xx AS t \n"
        + "WHERE (t.tenant_id = ? AND t.id = ? AND t.object_id = ? AND t.deleted = ?) \n"
        + "LIMIT 0, 1 \n"
        + " ARGS: [1, 1, 1, 0], COST:2 ms ";
    System.out.println(SqlUtil.parseQuery(sql));

  }

  @Test
  public void testInsert() throws Exception {
    String sql = "INSERT_SQL: INSERT INTO t_xx (`id`, `org_code`, `maintain_item`, `id`, `owner_id`, `object_id`, `tenant_id`, `created_at`, `created_by`, `deleted`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) , [1, Z00514, XM027, 2000050000054425038, 1524574160855036011, 1040105, 10000, Fri Dec 01 14:26:45 CST 2023, 1, false] ";

    System.out.println(SqlUtil.parseInsert(sql));
  }


  @Test
  public void testUpdate() throws Exception {
    String sql = "UPDATE_SQL : UPDATE t_xx SET `service_engineer_id` = ?, `service_engineer_name` = ?, `BUSINESS_DOCUMENT_ID` = ?, `service_engineer_type` = ?, `business_document_type` = ?, `updated_at` = ?, `updated_by` = ? WHERE id=? AND tenant_id=? [1526523747450802273, 111, 2000050000054444936, 5, 120, Fri Dec 01 14:51:36 CST 2023, 1526377387112058953, 2000050000054427068, 10000] ";
    System.out.println(SqlUtil.parseUpdate(sql));
  }

}
