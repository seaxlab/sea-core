# Fastjson sensitive

## Usage

### 方式1- 全局

````
FastJsonConfig config = new FastJsonConfig();
config.setSerializeFilters(new PhoneSerializeFilter());
````

### 方式2- 单次

````
log.info(JSON.toJSONString(userEntity, new SensitiveSerializeValueFilter()));

````