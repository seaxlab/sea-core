package com.github.seaxlab.core.component.ognl;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.ognl.member.DefaultMemberAccess;
import com.github.seaxlab.core.domain.Group;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import ognl.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@Slf4j
public class OgnlTest extends BaseCoreTest {

  OgnlContext context;

  @Before
  public void before() {
    // 创建上下文环境
    ClassResolver classResolver = new DefaultClassResolver();
    MemberAccess memberAccess = new DefaultMemberAccess(true);

    context = new OgnlContext(classResolver, null, memberAccess);
  }

  @Test
  public void run26() throws Exception {
    Object value = Ognl.getValue("substring(0,4)", context, "ognl3.2.6MemeberAccess");

    assertEquals("ognl", value);
  }

  @Test
  public void basicTest() throws Exception {
    // 创建Root对象
    User user = new User();
    user.setId(1L);
    user.setName("downpour");

    // 赋值
    context.put("introduction", "My name is ");

    // mybatis中封装成了OgnlCache

    // 测试从上下文环境中进行表达式计算并获取结果
    Object contextValue = Ognl.getValue(Ognl.parseExpression("#introduction"), context, user);
    assertEquals("My name is ", contextValue);

    // 测试同时从将Root对象和上下文环境作为表达式的一部分进行计算
    Object hello = Ognl.getValue(Ognl.parseExpression("#introduction + name"), context, user);
    assertEquals("My name is downpour", hello);
  }

  @Test
  public void testSetValue() throws Exception {
    // 创建Root对象
    User user = new User();
    user.setId(1L);
    user.setName("downpour");
    //重要 这里必须是赋值对象，否则报 setValue target is null
    user.setGroup(new Group());

    // 对Root对象进行写值操作
    //如果没有noSuchProperty.
    Ognl.setValue("age", context, user, "18");
    Ognl.setValue("group.name", context, user, "dev");

    assertEquals("dev", user.getGroup().getName());
  }
}
