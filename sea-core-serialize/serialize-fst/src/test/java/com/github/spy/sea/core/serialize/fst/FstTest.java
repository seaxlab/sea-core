package com.github.spy.sea.core.serialize.fst;

import com.github.spy.sea.core.loader.EnhancedServiceLoader;
import com.github.spy.sea.core.serialize.SerializeProcessor;
import com.github.spy.sea.core.test.AbstractCore5Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-15
 * @since 1.0
 */
@Slf4j
public class FstTest extends AbstractCore5Test {

    @Test
    public void run17() throws Exception {

        User user = new User();
        user.setId(100L);
        user.setUsername("123");

        SerializeProcessor serializeProcessor = EnhancedServiceLoader.load(SerializeProcessor.class, "fst");

        byte[] bytes = serializeProcessor.serialize(user);
        log.info("bytes={}", bytes);

        User user2 = serializeProcessor.deserialize(bytes, User.class);

        log.info("user2={}", user2);
    }
}
