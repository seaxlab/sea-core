package com.github.spy.sea.core.spring.component.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.EnumerablePropertySource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class EnumerablePropertySourceWrapper<T> extends EnumerablePropertySource<T> {

    private final PropertySourceWrapper<T> delegate;

    public EnumerablePropertySourceWrapper(EnumerablePropertySource<T> delegate) {
        super(delegate.getName(), delegate.getSource());
        this.delegate = new PropertySourceWrapper(delegate);
    }

    @Override
    public Object getProperty(String name) {
        return delegate.getProperty(name);
    }

    @Override
    public String[] getPropertyNames() {
        return ((EnumerablePropertySource) delegate.getDelegate()).getPropertyNames();
    }
}
