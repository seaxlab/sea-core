# CHANGE LOG

## 升级说明

### TODO

- OneBizService 优化
- json统一抽象,`component/json/unified`
- fastjson漏洞较多，减少fastjson的使用
- 大写数字
- tk select MAX/MIN
- tree模块，丰富AVLTree等
- 统一TreeNode
- spring/springboot test需要进行归类
- spring自定义注解扫描，类似@MapperScan
- 文件下载进度日志
- 网速显示

### 2.5.0-SNAPSHOT

- 减少对hutool的依赖
- sea-core-mybatis合并至sea-core-dal（统一数据访问层）中

### 2.3.0-SNAPSHOT

- MathUtil增加连续的是数组信息
- 增加Base62Util
- TxUtil增加post commit事务回调，适用于需要事务提交后再发送mq消息
- 增加独立logging模块，可以独立输出日志
- 增加服务线程ServiceThread，可立即唤醒执行
- RedisManager批量获取多个锁
- sea-core-model拆分出来，sea-core-basic依赖sea-core-model，尽量保证sea-core-model的稳定和少依赖 2021-04-20
- 增加tk InsertOrUpdateMapper便于减少sql语句，其中OGNL很不错，在静态化之前进行预编译
- add murmur hash
- fix HttpClientUtil中Response的status code
- 文件下载器、文件切割/合并
- 集成alibaba spring-context-support 2021-02-24
- 增加spring 方法级注解扫描、属性级注入 2021-02-23
- fix classpath 读取文件
- 增加`ClassScan`扫描指定class/method.
- 封装webflux `WebClientUtil`
- 增加boot-stater支持`sea.env`，`sea.region`,`sea.profile`变量 2-1
- 增加`simple token interceptor`
- RequestUtil增加 `header-> request -> cookie` 优先级获取方法
- 规范化所有test基类，尤其是spring、springboot类
- 增加mockserver支持

### 2.0.0 2019-07-13

- 2.0.0 正式版本

### 1.0.0 2019-05-13

- 初始版本