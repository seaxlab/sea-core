package com.github.spy.sea.core.support.notify;

import com.github.spy.sea.core.support.notify.manager.impl.DingDingNotifyManager;
import com.github.spy.sea.core.test.AbstractCore5Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
@Slf4j
public class NotifyManagerTest extends AbstractCore5Test {

    @Test
    public void run17() throws Exception {
        String endpoint = "https://oapi.dingtalk.com/robot/send?access_token=4a7f096e3d230cdd4a468ae7549d452b65cf7b66b88bc8255ee5438a2f8b3732";


        DingDingNotifyManager notifyManager = new DingDingNotifyManager();

        notifyManager.setEndpoint(endpoint);

        notifyManager.send("1");
    }
}
