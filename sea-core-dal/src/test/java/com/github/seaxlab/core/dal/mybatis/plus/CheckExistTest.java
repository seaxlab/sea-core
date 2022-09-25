package com.github.seaxlab.core.dal.mybatis.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.seaxlab.core.spring.tx.util.TxUtil;
import com.github.seaxlab.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/7
 * @since 1.0
 */
@Slf4j
public class CheckExistTest extends BasePlusTest {

    /**
     * plus
     */
    @Resource
    private User2Mapper user2Mapper;

    @Test
    public void testSimple() throws Exception {
        //boolean exist = user2Mapper.checkExist(User2::getId, 1);
        //log.info("exist={}", exist);
    }

    @Test
    public void testWrapper() throws Exception {
        User2 user2 = new User2();
        QueryWrapper<User2> wrapper = Wrappers.query(user2);
        wrapper.eq("id", 100);

        boolean exist = user2Mapper.checkExist(wrapper);
        log.info("exist={}", exist);
    }

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Resource
    private User2Service user2Service;

    @Test
    public void testTx() throws Exception {
        TransactionStatus txStatus = TxUtil.begin(dataSourceTransactionManager);
        try {
            User2 entity = new User2();
            entity.setName("t_" + IdUtil.getYYYYMMDDHHMMSSSSS());
            user2Mapper.insert(entity);
            user2Service.add();
            TxUtil.commit(dataSourceTransactionManager, txStatus);
        } catch (Exception e) {
            log.error("ee", e);
            TxUtil.rollback(dataSourceTransactionManager, txStatus);
        }
    }

    @Test
    public void testTx2() throws Exception {
        user2Service.add2();
    }

}
