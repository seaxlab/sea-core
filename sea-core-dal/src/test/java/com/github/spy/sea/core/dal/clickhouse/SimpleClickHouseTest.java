package com.github.spy.sea.core.dal.clickhouse;

import com.github.spy.sea.core.dal.BaseCoreDalTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;
import ru.yandex.clickhouse.settings.ClickHouseQueryParam;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/22
 * @since 1.0
 */
@Slf4j
public class SimpleClickHouseTest extends BaseCoreDalTest {


    @Test
    public void test17() throws Exception {
        String url = "jdbc:clickhouse://mylab:8123/tutorial";
        ClickHouseProperties properties = new ClickHouseProperties();
// set connection options - see more defined in ClickHouseConnectionSettings
        properties.setClientName("Agent #1");
// set default request options - more in ClickHouseQueryParam
        properties.setSessionId("default-session-id");
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        String sql = "select * from hits_v1 limit 0,100";
        Map<ClickHouseQueryParam, String> additionalDBParams = new HashMap<>();
// set request options, which will override the default ones in ClickHouseProperties
        additionalDBParams.put(ClickHouseQueryParam.SESSION_ID, "new-session-id");

        List<Map> list = new ArrayList();

        try (ClickHouseConnection conn = dataSource.getConnection();
             ClickHouseStatement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql, additionalDBParams)) {
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
                }
                list.add(map);
            }
        }

        log.info("list={}", list);
    }
}
