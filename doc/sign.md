# 签名模块

## GET 示例

````
http://localhost:8080/signTest?sign=A0161DC47118062053567CDD10FBACC6&username=admin&password=admin
````

A0161DC47118062053567CDD10FBACC6 是 username=admin&password=admin MD5加密后的结果。

## POST 示例1

请求Url为 http://localhost:8080/signTest?sign=A0161DC47118062053567CDD10FBACC6 参数为

````
{
    "username":"admin",
    "password":"admin"
}
````

## POST 示例2

签名都在 request body中 使用这种方式时，需要注意 request body流只能读取一次

````
    CloseableHttpClient client = HttpClients.createDefault();
    
    // appid
    String appid = new String("a00000000000000000000000000000001");
    
    // secret秘钥
    String secret = new String("2aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa0");
    
    // json方式
    JSONObject jsonParam = new JSONObject();
    jsonParam.put("appId", appid);
    // 生成随机字符串
    String str = Utils.createNonceStr();
    jsonParam.put("nonceStr", str);
    
    // 时间戳
    String date = String.valueOf(System.currentTimeMillis() / 1000);
    jsonParam.put("timestamp", date);
    
    // 生成签名
    String waitSign = "appId=" + appid + "&nonceStr=" + str + "&secret=" + secret + "&timestamp=" + date;
    String sign = Utils.sha256(waitSign.getBytes());
    jsonParam.put("signature", sign);
    System.out.println(jsonParam.toString());
    
    // 解决中文乱码问题
    StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    
    httpPost.setEntity(entity);
    
    String respContent = null;
    HttpResponse resp = client.execute(httpPost);
    
    if (resp.getStatusLine().getStatusCode() == 200) {
       HttpEntity he = resp.getEntity();
       respContent = EntityUtils.toString(he, "UTF-8");
    }
````