package com.github.seaxlab.core.support.notify;

import com.github.seaxlab.core.support.BaseSupportTest;
import com.github.seaxlab.core.support.notify.dto.FeiShuNotifyDTO;
import com.github.seaxlab.core.support.notify.enums.MsgTypeEnum;
import com.github.seaxlab.core.support.notify.manager.impl.FeiShuNotifyManager;
import com.github.seaxlab.core.support.notify.util.FeiShuUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.FreemarkerUtil;
import com.github.seaxlab.core.util.MessageUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

  private String url;

  @Before
  public void before() {
    String accessToken = getConfig("feishu_access_token");
    url = MessageUtil.format(FeiShuUtil.URL_SIMPLE, accessToken);
  }

  @Test
  public void test17() throws Exception {

    FeiShuNotifyManager notifyManager = new FeiShuNotifyManager();
    notifyManager.setEndpoint(url);
    FeiShuNotifyDTO dto = new FeiShuNotifyDTO();
    dto.setTitle("test");
    dto.setContent("test");
    notifyManager.send(dto);
  }

  @Test
  public void testInteractive() throws Exception {
    FeiShuNotifyManager notifyManager = new FeiShuNotifyManager();
    notifyManager.setEndpoint(url);
    FeiShuNotifyDTO dto = new FeiShuNotifyDTO();
    dto.setMsgTypeEnum(MsgTypeEnum.INTERACTIVE);
    dto.setTitle("test");

    String content = FileUtil.readFormClasspath("feishu/card.json");
    dto.setContent(content);
    notifyManager.send(dto);
  }

  @Test
  public void test57() throws Exception {
    FreemarkerUtil.initConfig(FeiShuNotifyManagerTest.class.getClassLoader(), "/");

    FeiShuNotifyManager notifyManager = new FeiShuNotifyManager();
    notifyManager.setEndpoint(url);
    FeiShuNotifyDTO dto = new FeiShuNotifyDTO();
    dto.setMsgTypeEnum(MsgTypeEnum.INTERACTIVE);
    dto.setTitle("test");

    Map<String, Object> params1 = new HashMap<>();
    String message = FreemarkerUtil.render("feishu/message.ftl", params1);

    String content = FileUtil.readFormClasspath("feishu/message-warning.json");

    Map<String, String> params = new HashMap<>();
    params.put("content", message);
    String text = MessageUtil.replace(content, params);

    dto.setContent(text);
    notifyManager.send(dto);
  }

}
