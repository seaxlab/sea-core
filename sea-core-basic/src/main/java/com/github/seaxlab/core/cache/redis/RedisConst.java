package com.github.seaxlab.core.cache.redis;

import lombok.extern.slf4j.Slf4j;

/**
 * @author spy
 * @version 1.0 2021/5/30
 * @since 1.0
 */
@Slf4j
public class RedisConst {
  public static final String LUA_GENERATE_ID = "generate_id.lua";
  public static final String LUA_GENERATE_ID_BATCH = "generate_id_batch.lua";

  public static final String LUA_BATCH_INCR = "batch_incr.lua";
  public static final String LUA_BATCH_INCR_LIMIT = "batch_incr_limit.lua";
  public static final String LUA_BATCH_DECR = "batch_decr.lua";
  public static final String LUA_BATCH_DECR_LIMIT = "batch_decr_limit.lua";

  /**
   * lua 中true，对应redis 1
   */
  public static final Long LUA_BOOLEAN_TRUE = 1L;

  //Redis 到 Lua 的转换表。
  //
  //Redis integer reply -> Lua number / Redis 整数转换成 Lua 数字
  //Redis bulk reply -> Lua string / Redis bulk 回复转换成 Lua 字符串
  //Redis multi bulk reply -> Lua table (may have other Redis data types nested) / Redis 多条 bulk 回复转换成 Lua 表，表内可能有其他别的 Redis 数据类型
  //Redis status reply -> Lua table with a single ok field containing the status / Redis 状态回复转换成 Lua 表，表内的 ok 域包含了状态信息
  //Redis error reply -> Lua table with a single err field containing the error / Redis 错误回复转换成 Lua 表，表内的 err 域包含了错误信息
  //Redis Nil bulk reply and Nil multi bulk reply -> Lua false boolean type / Redis 的 Nil 回复和 Nil 多条回复转换成 Lua 的布尔值 false
  //Lua 到 Redis 的转换表。
  //
  //Lua number -> Redis integer reply (the number is converted into an integer) / Lua 数字转换成 Redis 整数
  //Lua string -> Redis bulk reply / Lua 字符串转换成 Redis bulk 回复
  //Lua table (array) -> Redis multi bulk reply (truncated to the first nil inside the Lua array if any) / Lua 表(数组)转换成 Redis 多条 bulk 回复
  //Lua table with a single ok field -> Redis status reply / 一个带单个 ok 域的 Lua 表，转换成 Redis 状态回复
  //Lua table with a single err field -> Redis error reply / 一个带单个 err 域的 Lua 表，转换成 Redis 错误回复
  //Lua boolean false -> Redis Nil bulk reply. / Lua 的布尔值 false 转换成 Redis 的 Nil bulk 回复
  //从 Lua 转换到 Redis 有一条额外的规则，这条规则没有和它对应的从 Redis 转换到 Lua 的规则：
  //
  //Lua boolean true -> Redis integer reply with value of 1. / Lua 布尔值 true 转换成 Redis 整数回复中的 1
}
