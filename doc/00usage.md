# Usage

### 非spring-boot类工程

````
<properties>
    <!--请取最新版本-->
    <sea.core.version>x.y.z</sea.core.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.github.seaxlab</groupId>
        <artifactId>sea-core-basic</artifactId>
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
        <groupId>com.github.seaxlab</groupId>
        <artifactId>sea-core-spring-boot-starter</artifactId>
        <version>${sea.core.version}</version>
    </dependency>
</dependencies>

````

### 注意事项

- 如果用了`JSON.isValid()`,则必须升级到1.2.25及以上
- 统一使用Jackson，禁止直接使用json库API,便于统一管理和升级。fastjson有一些潜在的漏洞问题
- Result只适用用在外部接口之间的交互