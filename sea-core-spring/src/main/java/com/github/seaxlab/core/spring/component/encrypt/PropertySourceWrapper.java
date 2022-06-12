package com.github.seaxlab.core.spring.component.encrypt;

import com.github.seaxlab.core.security.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class PropertySourceWrapper<T> extends PropertySource<T> {

    private final PropertySource<T> delegate;

    private static final Map<String, String> cache = new HashMap<>();

    private static final String PREFIX_ENCRYPT = "SEA_AES";


    public PropertySourceWrapper(PropertySource<T> delegate) {
        super(delegate.getName(), delegate.getSource());
        this.delegate = delegate;
    }

    public PropertySource getDelegate() {
        return this.delegate;
    }

    @Override
    public Object getProperty(String name) {
        synchronized (cache) {
            Object property = delegate.getProperty(name);
            if (property instanceof String) {
                String value = ((String) property).trim();
                if (value.matches(PREFIX_ENCRYPT + "\\(.+\\)")) {
                    // get value from (xxx)
                    String encrypt = value.replaceAll("^" + PREFIX_ENCRYPT + "\\((.+)\\)$", "$1").trim();
                    if (cache.containsKey(encrypt)) {
                        return cache.get(encrypt);
                    } else {
                        try {
                            //TODO decrypt
                            String decrypt = AESUtil.decrypt(encrypt, "xxx");
                            cache.put(encrypt, decrypt);
                        } catch (Exception e) {
                            log.error("fail to decrypt value", e);
                        }
                    }
                }
            }
            return property;
        }
    }
}
