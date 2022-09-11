# LUA

## request_rate_limiter.lua

- 通过KEYS[1] 获取传入的key参数
- 通过ARGV[1]获取传入的limit参数
- redis.call方法，从缓存中get和key相关的值，如果为null那么就返回0
- 接着判断缓存中记录的数值是否会大于限制大小，如果超出表示该被限流，返回0
- 如果未超过，那么该key的缓存值+1，并设置过期时间为1秒钟以后，并返回缓存值+1