package com.github.seaxlab.core.support.notify;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.support.notify.dto.DingDingNotifyDTO;
import com.github.seaxlab.core.support.notify.enums.MsgTypeEnum;
import com.github.seaxlab.core.support.notify.manager.impl.DingDingNotifyManager;
import com.github.seaxlab.core.support.notify.util.DingDingUtil;
import com.github.seaxlab.core.test.AbstractCore5Test;
import com.github.seaxlab.core.util.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
@Slf4j
public class DingDingNotifyManagerTest extends AbstractCore5Test {

  @Test
  public void testSign() throws Exception {

    //签名方式
    String accessToken = getConfig("sea.dingding.sign.access_token");
    Long timestamp = System.currentTimeMillis();
    String secret = getConfig("sea.dingding.sign.secret");

    String url = TemplateUtil.format(DingDingUtil.URL_SIGN, accessToken, timestamp.toString(),
      DingDingUtil.getSign(timestamp, secret));

    DingDingNotifyManager notifyManager = new DingDingNotifyManager();

    notifyManager.setEndpoint(url);

    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setContent("1");
    notifyManager.send(dto);
  }

  @Test
  public void testAtAll() throws Exception {

    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setContent("111");

    List<String> mobiles = new ArrayList<>();
    mobiles.add("10086");

    DingDingNotifyDTO.At at = new DingDingNotifyDTO.At();

    at.setAtMobiles(mobiles);
    at.setAtAll(true);
    dto.setAt(at);

    // {"at":{"atMobiles":["10086"],"isAtAll":true},"content":"111"}
    log.info("{}", JSONObject.toJSONString(dto));

  }

  @Test
  public void testDingDingText() throws Exception {
    String phone = getConfig("sea.phone");
    String accessToken = getConfig("sea.dingding.access_token");
    String url = TemplateUtil.format(DingDingUtil.URL_SIMPLE, accessToken);

    DingDingNotifyManager notifyManager = new DingDingNotifyManager();
    notifyManager.setEndpoint(url);

    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setContent("test报警");

    List<String> mobiles = new ArrayList<>();
    mobiles.add(phone);

    DingDingNotifyDTO.At at = new DingDingNotifyDTO.At();

    at.setAtMobiles(mobiles);
    dto.setAt(at);

    notifyManager.send(dto);
  }

  @Test
  public void testDingDingMarkdown() throws Exception {
    String phone = getConfig("sea.phone");
    StringBuilder sb = new StringBuilder();
    //sb.append("# test报警\n");
    //sb.append("## 二级标题\n");
    //sb.append("- [this is a link](http://www.baidu.com)\n");
    //sb.append("- traceId=123456\n");
    //sb.append("\n\n");
    //// TODO 重点这里需要加入
    //sb.append("@"+phone);

    sb.append("#### <font color=\"#FF0000\">S3 - Triggered - http_request_error</font>\n" + "\n" + "---\n" + "\n"
      + "- **规则标题**: http_request_error\n" + "- **规则备注**: http_request_error\n"
      + "- **监控指标**: [a=1 env=pro ident=app1 region=beijing tag1= tag2= tag3=]\n"
      + "- **触发时间**: 2022-01-06 14:47:38\n" + "- **触发时值**: 283\n" + "- **发送时间**: 2022-01-06 14:48:15\n"
      + "\n" + "\n" + "- traceId=96a10926-1f41-46bc-b630-618a20456d41\n"
      + "- [请求链路](http://jaeger.xxx.com/trace/96a10926-1f41-46bc-b630-618a20456d41)\n" + "- 备注：228\n" + "\n"
      + "Power By Sea Monitor Framework\n" + "\n" + "@" + phone);

    String accessToken = getConfig("sea.dingding.access_token");
    String url = TemplateUtil.format(DingDingUtil.URL_SIMPLE, accessToken);

    DingDingNotifyManager notifyManager = new DingDingNotifyManager();
    notifyManager.setEndpoint(url);

    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setMsgTypeEnum(MsgTypeEnum.MARKDOWN);
    dto.setTitle("test");
    dto.setContent(sb.toString());

    DingDingNotifyDTO.At at = new DingDingNotifyDTO.At();
    at.addMobile(phone);
    dto.setAt(at);

    notifyManager.send(dto);


  }


}
