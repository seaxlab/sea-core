package com.github.seaxlab.core.support.notify;

import com.github.seaxlab.core.support.BaseSupportTest;
import com.github.seaxlab.core.support.notify.dto.FeiShuNotifyDTO;
import com.github.seaxlab.core.support.notify.manager.impl.FeiShuNotifyManager;
import com.github.seaxlab.core.support.notify.util.FeiShuUtil;
import com.github.seaxlab.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/30
 * @since 1.0
 */
@Slf4j
public class FeiShuNotifyManagerTest extends BaseSupportTest {

    @Test
    public void test17() throws Exception {
        String accessToken = getPassword("feishu_access_token");
        String url = MessageUtil.format(FeiShuUtil.URL_SIMPLE, accessToken);

        FeiShuNotifyManager notifyManager = new FeiShuNotifyManager();
        notifyManager.setEndpoint(url);
        FeiShuNotifyDTO dto = new FeiShuNotifyDTO();
        dto.setTitle("test");
        dto.setContent("test");
        notifyManager.send(dto);
    }
}
