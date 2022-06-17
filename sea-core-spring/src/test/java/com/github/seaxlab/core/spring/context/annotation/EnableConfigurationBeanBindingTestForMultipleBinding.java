package com.github.seaxlab.core.spring.context.annotation;

/**
 * {@link com.github.seaxlab.core.spring.context.annotation.EnableConfigurationBeanBinding} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.3
 */

import com.github.seaxlab.core.spring.context.config.DefaultConfigurationBeanBinder;
import com.github.seaxlab.core.spring.model.User;
import com.github.seaxlab.core.spring.util.BeanUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@EnableConfigurationBeanBinding(prefix = "users", type = User.class,
        multiple = true, ignoreUnknownFields = false, ignoreInvalidFields = false)
public class EnableConfigurationBeanBindingTestForMultipleBinding extends AbstractEnableConfigurationBeanBindingTest {

    @Bean
    public ConfigurationBeanBindingPostProcessor configurationBeanBindingPostProcessor() {
        ConfigurationBeanBindingPostProcessor processor = new ConfigurationBeanBindingPostProcessor();
        processor.setConfigurationBeanBinder(new DefaultConfigurationBeanBinder());
        return processor;
    }

    private User aUser;

    private User bUser;

    private User mUser;

    private Collection<User> users;

    private ConfigurationBeanBindingPostProcessor configurationBeanBindingPostProcessor;

    @Before
    public void init() {
        aUser = context.getBean("a", User.class);
        bUser = context.getBean("b", User.class);
        users = BeanUtil.getSortedBeans(context, User.class);
        configurationBeanBindingPostProcessor = context.getBean("configurationBeanBindingPostProcessor", ConfigurationBeanBindingPostProcessor.class);
    }

    @Test
    public void testUser() {

        assertEquals(2, users.size());
        assertTrue(users.contains(aUser));
        assertTrue(users.contains(bUser));

        assertEquals("name-a", aUser.getName());
        assertEquals(1, aUser.getAge());

        assertEquals("name-b", bUser.getName());
        assertEquals(2, bUser.getAge());

        assertNotNull(configurationBeanBindingPostProcessor.getConfigurationBeanBinder());
    }
}
