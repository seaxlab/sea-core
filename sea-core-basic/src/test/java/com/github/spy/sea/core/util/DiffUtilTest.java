package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.Role;
import com.github.spy.sea.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.diff.Diff;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class DiffUtilTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("abc");
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setCode("12" + i);
            roles.add(role);
        }
        user.setRoles(roles);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("abc12");

        List<Role> roles2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setCode("12" + i);
            roles2.add(role);
        }
        user2.setRoles(roles2);

        Diff diff = DiffUtil.compare(user, user2);

        log.info("diff={}", diff);
        diff.getChanges();
    }
}
