package com.github.seaxlab.core.spring.context.annotation;

import com.github.seaxlab.core.spring.model.User;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertEquals;

/**
 * {@link EnableConfigurationBeanBindings} Test cases
 *
 * @since 1.0.4
 */
@EnableConfigurationBeanBindings(
        @EnableConfigurationBeanBinding(prefix = "usr", type = User.class)
)
@Configuration
public class EnableConfigurationBeanBindingsTest extends AbstractEnableConfigurationBeanBindingTest {

    @Test
    public void testUser() {
        User user = context.getBean("m", User.class);
        assertEquals("mercyblitz", user.getName());
        assertEquals(34, user.getAge());
    }
}
