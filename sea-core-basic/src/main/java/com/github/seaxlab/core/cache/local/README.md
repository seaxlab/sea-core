# Local Cache

## 使用Guava LoadingCache

````
    LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                                                            .build(new CacheLoader<String, String>() {
                                                                @Override
                                                                public String load(String key) throws Exception {
                                                                    log.info("load data really.");
                                                                    return RandomUtil.shortUUID();
                                                                }
                                                            });

    String key1 = "key1";
    String value1 = loadingCache.get(key1);
    log.info("value1={}", value1);

    // 刷新key1的值（异步）
    loadingCache.refresh(key1);


    // 驱逐一个key（同步）
    loadingCache.invalidate(key1);
````