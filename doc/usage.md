# Usage

### 非spring-boot类工程

````
<properties>
    <!--请取最新版本-->
    <sea.core.version>x.y.z</sea.core.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.github.spy.sea</groupId>
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
        <groupId>com.github.spy.sea</groupId>
        <artifactId>sea-core-spring-boot-starter</artifactId>
        <version>${sea.core.version}</version>
    </dependency>
</dependencies>

````