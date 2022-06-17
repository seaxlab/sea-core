package com.github.seaxlab.core.dal.screw;

import com.github.seaxlab.core.dal.screw.dto.DBModelCreateDTO;
import com.github.seaxlab.core.dal.screw.util.ScrewUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/23
 * @since 1.0
 */
@Slf4j
public class MiaoSuanTest extends ScrewTest {

    @Test
    public void testB2bTest() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/b2b-test");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("b2b-test"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testB2bCrawler() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/b2b-crawler");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("b2b-crawler"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testB2bTinscon() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/b2b-tinscon");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("b2b-tinscon"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testMsOrder() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/ms-order");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("ms-order"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testMsProduct() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/ms-product");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("ms-product"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }

    @Test
    public void testMsShop() throws Exception {
        DBModelCreateDTO dto = new DBModelCreateDTO();
        dto.setUrl("jdbc:mysql://rm-bp1351y9g948365jazo.mysql.rds.aliyuncs.com:3306/ms-shop");
        dto.setUsername("miaosuan");
        dto.setPassword(getPassword("db_miaosuan"));

        dto.setOutPutFileName(getFileName("ms-shop"));
        dto.setVersion("1.0.0");

        ScrewUtil.dump(dto);
    }


}
