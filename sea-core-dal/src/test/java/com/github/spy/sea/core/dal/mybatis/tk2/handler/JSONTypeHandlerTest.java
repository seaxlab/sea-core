package com.github.spy.sea.core.dal.mybatis.tk2.handler;

import com.github.spy.sea.core.dal.mybatis.tk.handler.JSONTypeHandler;
import com.github.spy.sea.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class JSONTypeHandlerTest extends BaseTypeHandlerTest {

    private static final TypeHandler<User> TYPE_HANDLER = new JSONTypeHandler<User>();

    //    @Mock
    private User user;

    @BeforeEach
    public void before() {
        user = new User();
        user.setId(0L);
        user.setUsername("ss");
    }

    @Override
    @Test
    @DisplayName("设置参数")
    public void shouldSetParameter() throws Exception {
        TYPE_HANDLER.setParameter(ps, 1, user, null);
        verify(ps).setString(1, JSONUtil.toStr(user));
    }

    @Override
    public void shouldGetResultFromResultSetByName() throws Exception {

    }

    @Override
    public void shouldGetResultNullFromResultSetByName() throws Exception {

    }

    @Override
    public void shouldGetResultFromResultSetByPosition() throws Exception {

    }

    @Override
    public void shouldGetResultNullFromResultSetByPosition() throws Exception {

    }

    @Override
    public void shouldGetResultFromCallableStatement() throws Exception {

    }

    @Override
    public void shouldGetResultNullFromCallableStatement() throws Exception {

    }
}
