# sea-core

## 注意事项

- 如果用了`JSON.isValid()`,则必须升级到1.2.25及以上

## 使用说明

### 非spring-boot类工程

````
<properties>
    <!--请取最新版本-->
    <sea.core.version>x.y.z</sea.core.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.github.spy.sea</groupId>
        <artifactId>sea-core-all</artifactId>
        <version>${sea.core.version}</version>
    </dependency>
</dependencies>

````

### spring-boot类工程

````
<properties>
    <!--请取最新版本-->
    <sea.core.version>x.y.z</sea.core.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.github.spy.sea</groupId>
        <artifactId>sea-core-spring-boot-starter</artifactId>
        <version>${sea.core.version}</version>
    </dependency>
</dependencies>

````

## 升级说明

### 2.3.0-SNAPSHOT 2020-09-08

- 增加boot-stater支持`sea.env`变量
- 增加`simple token interceptor`
- RequestUtil增加 `header-> request -> cookie` 优先级获取方法
- 规范化所有test基类，尤其是spring、springboot类
- 增加mockserver支持

### 2.0.0 2019-07-13

- 2.0.0 正式版本

### 1.0.0 2019-05-13

- 初始版本