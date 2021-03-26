# sea-core

## 注意事项

- 如果用了`JSON.isValid()`,则必须升级到1.2.25及以上
- 统一使用JSONUtil，禁止直接使用json库API。fastjson有一些潜在的问题

## 使用说明

- [Usage](doc/usage.md)

## 升级说明

### TODO

- spring/springboot test需要进行归类
- spring自定义注解扫描，类似@MapperScan
- fastjson漏洞较多
- sea-core-model拆分出来，sea-core-basic依赖sea-core-model，尽量保证sea-core-model的稳定和少依赖
- 文件下载进度日志
- 网速显示

### 2.3.0-SNAPSHOT

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