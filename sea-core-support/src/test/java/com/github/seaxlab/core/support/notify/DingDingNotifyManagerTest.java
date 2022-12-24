package com.github.seaxlab.core.support.notify;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.support.notify.dto.DingDingNotifyDTO;
import com.github.seaxlab.core.support.notify.enums.MsgTypeEnum;
import com.github.seaxlab.core.support.notify.manager.impl.DingDingNotifyManager;
import com.github.seaxlab.core.support.notify.util.DingDingUtil;
import com.github.seaxlab.core.test.AbstractCore5Test;
import com.github.seaxlab.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    String accessToken = "96ad13be7b5e8fdd86f8b7c1c089b799127abf6e6c558458cce722187a0c4c1f";
    Long timestamp = System.currentTimeMillis();
    String secret = "SECb8d39ac9513cfd90d248c55e6bfbd830b02da2260b9da6a96a550932585ad9ed";

    String url = MessageUtil.format(DingDingUtil.URL_SIGN, accessToken, timestamp.toString(), DingDingUtil.getSign(timestamp, secret));


    DingDingNotifyManager notifyManager = new DingDingNotifyManager();

    notifyManager.setEndpoint(url);

    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setContent("1");
    notifyManager.send(dto);
  }

  @Test
  public void run39() throws Exception {

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
    String accessToken = "899cd89c736c068e112a1f00882ba8fcd6abc5f8e5459cf82d7544efde4102f2";
    String url = MessageUtil.format(DingDingUtil.URL_SIMPLE, accessToken);

    DingDingNotifyManager notifyManager = new DingDingNotifyManager();
    notifyManager.setEndpoint(url);


    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setContent("test报警");

    List<String> mobiles = new ArrayList<>();
    mobiles.add("17626672199");

    DingDingNotifyDTO.At at = new DingDingNotifyDTO.At();

    at.setAtMobiles(mobiles);
    dto.setAt(at);

    notifyManager.send(dto);
  }

  @Test
  public void testDingDingMarkdown() throws Exception {
    StringBuilder sb = new StringBuilder();
    //sb.append("# test报警\n");
    //sb.append("## 二级标题\n");
    //sb.append("- [this is a link](http://www.baidu.com)\n");
    //sb.append("- traceId=123456\n");
    //sb.append("\n\n");
    //// TODO 重点这里需要加入
    //sb.append("@17626672199");

    sb.append("#### <font color=\"#FF0000\">S3 - Triggered - http_request_error</font>\n" +
      "\n" +
      "---\n" +
      "\n" +
      "- **规则标题**: http_request_error\n" +
      "- **规则备注**: http_request_error\n" +
      "- **监控指标**: [a=1 env=pro ident=app1 region=beijing tag1= tag2= tag3=]\n" +
      "- **触发时间**: 2022-01-06 14:47:38\n" +
      "- **触发时值**: 283\n" +
      "- **发送时间**: 2022-01-06 14:48:15\n" +
      "\n" +
      "\n" +
      "- traceId=96a10926-1f41-46bc-b630-618a20456d41\n" +
      "- [请求链路](http://jaeger.xxx.com/trace/96a10926-1f41-46bc-b630-618a20456d41)\n" +
      "- 备注：228\n" +
      "\n" +
      "Power By Sea Monitor Framework\n" +
      "\n" +
      "@17626672199 ");


    String accessToken = "899cd89c736c068e112a1f00882ba8fcd6abc5f8e5459cf82d7544efde4102f2";
    String url = MessageUtil.format(DingDingUtil.URL_SIMPLE, accessToken);

    DingDingNotifyManager notifyManager = new DingDingNotifyManager();
    notifyManager.setEndpoint(url);


    DingDingNotifyDTO dto = new DingDingNotifyDTO();
    dto.setMsgTypeEnum(MsgTypeEnum.MARKDOWN);
    dto.setTitle("test");
    dto.setContent(sb.toString());

    DingDingNotifyDTO.At at = new DingDingNotifyDTO.At();
    at.addMobile("17626672199");
    dto.setAt(at);

    notifyManager.send(dto);


  }


}
