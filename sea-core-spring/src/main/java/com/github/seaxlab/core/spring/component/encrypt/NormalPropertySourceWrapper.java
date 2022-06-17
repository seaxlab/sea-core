package com.github.seaxlab.core.spring.component.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.PropertySource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class NormalPropertySourceWrapper<T> extends PropertySource<T> {

    private final PropertySourceWrapper<T> delegate;

    public NormalPropertySourceWrapper(PropertySource<T> delegate) {
        super(delegate.getName(), delegate.getSource());
        this.delegate = new PropertySourceWrapper(delegate);
    }

    @Override
    public Object getProperty(String name) {
        return delegate.getProperty(name);
    }
}
