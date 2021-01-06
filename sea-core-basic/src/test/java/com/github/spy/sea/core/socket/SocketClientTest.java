package com.github.spy.sea.core.socket;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.socket.model.SocketClientSendData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/6
 * @since 1.0
 */
@Slf4j
public class SocketClientTest extends BaseCoreTest {

    @Test
    public void clientTest() throws Exception {

        runInMultiThread(() -> {
            SocketClientHelper socketClientHelper = SocketClientHelper.getInstance("127.0.0.1", 9001);

            GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
            genericObjectPoolConfig.setMaxTotal(200);
            socketClientHelper.setConfiguration(genericObjectPoolConfig);
            SocketClient socketClient = socketClientHelper.getSocket();

            for (int i = 0; i < 10; i++) {

                SocketClientSendData data = new SocketClientSendData();
                data.setPlayLoad(("hello world" + i++).getBytes(StandardCharsets.UTF_8));
                byte[] resp = socketClient.send(data);
                log.info("resp={}", new String(resp, StandardCharsets.UTF_8));

                sleep(new Random().nextInt(20));
            }
            socketClientHelper.returnSocket(socketClient);
            socketClientHelper.shutdown();
        });

        sleepMinute(10);
    }

}
