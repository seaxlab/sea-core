# sea-core

## 注意事项

- 如果用了`JSON.isValid()`,则必须升级到1.2.25及以上

## 使用说明

- [Usage](doc/usage.md)

## 升级说明

### 2.3.0-SNAPSHOT

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