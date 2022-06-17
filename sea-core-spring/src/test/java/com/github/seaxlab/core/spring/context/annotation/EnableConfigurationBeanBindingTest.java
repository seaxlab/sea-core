package com.github.seaxlab.core.spring.context.annotation;

/**
 * {@link com.github.seaxlab.core.spring.context.annotation.EnableConfigurationBeanBinding} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.3
 */

import com.github.seaxlab.core.spring.context.config.ConfigurationBeanBinder;
import com.github.seaxlab.core.spring.context.config.ConfigurationBeanCustomizer;
import com.github.seaxlab.core.spring.context.config.DefaultConfigurationBeanBinder;
import com.github.seaxlab.core.spring.model.User;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import static org.junit.Assert.assertEquals;


@EnableConfigurationBeanBinding(prefix = "usr", type = User.class)
public class EnableConfigurationBeanBindingTest extends AbstractEnableConfigurationBeanBindingTest {

    @Bean
    public ConfigurationBeanCustomizer customizer() {
        return new ConfigurationBeanCustomizer() {

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public void customize(String beanName, Object configurationBean) {
                if ("m".equals(beanName) && configurationBean instanceof User) {
                    User user = (User) configurationBean;
                    user.setAge(19);
                }
            }
        };
    }

    @Bean
    public ConfigurationBeanBinder configurationBeanBinder() {
        return new DefaultConfigurationBeanBinder();
    }

    @Test
    public void testUser() {
        User user = context.getBean("m", User.class);
        assertEquals("mercyblitz", user.getName());
        assertEquals(19, user.getAge());
    }
}
