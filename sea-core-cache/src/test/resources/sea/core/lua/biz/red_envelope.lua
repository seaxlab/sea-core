-- 抢红包脚本
--[[
--red:list 为 List 结构，存放预先生成的红包金额
red:draw_count:u:openid 为 k-v 结构，用户领取红包计数器
red:draw为 Hash 结构，存放红包领取记录
red:task 也为 List 结构，红包异步发放队列
openid 为用户的openid
]]--
local prefix = KEYS[1]
local openid = KEYS[2]
local isDraw = redis.call("HEXISTS",prefix..":red_envelope:draw",openid)
-- 已经领取
if isDraw ~= 0 then
    return true
end
-- 领取太多次了
local times = redis.call("INCR",prefix..":red_envelope:draw_count:u:"..openid)
if times and tonumber(times) > 9 then
    return 0
end
local number = redis.call("RPOP",prefix..":red_envelope:list")
-- 没有红包
if not number then
    return {}
end
-- 领取人昵称为Fhb,头像为 https:// xxxxxx
local red = {money=number,name=KEYS[3] , pic = KEYS[4] }
-- 领取记录
redis.call("HSET", prefix..":red_envelope:draw",openid,cjson.encode(red))
-- 处理队列
red["openid"] = openid
redis.call("RPUSH", prefix..":red_envelope:task",cjson.encode(red))

return true