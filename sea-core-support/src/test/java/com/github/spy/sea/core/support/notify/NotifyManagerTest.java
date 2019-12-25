package com.github.spy.sea.core.support.notify;

import com.github.spy.sea.core.message.util.MessageUtil;
import com.github.spy.sea.core.support.notify.manager.impl.DingDingNotifyManager;
import com.github.spy.sea.core.support.notify.util.DingDingUtil;
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

        //签名方式
        String accessToken = "96ad13be7b5e8fdd86f8b7c1c089b799127abf6e6c558458cce722187a0c4c1f";
        Long timestamp = System.currentTimeMillis();
        String secret = "SECb8d39ac9513cfd90d248c55e6bfbd830b02da2260b9da6a96a550932585ad9ed";

        String url = MessageUtil.format(DingDingUtil.URL_SIGN, accessToken, timestamp.toString(), DingDingUtil.getSign(timestamp, secret));


        DingDingNotifyManager notifyManager = new DingDingNotifyManager();

        notifyManager.setEndpoint(url);

        notifyManager.send("1");
    }


}
