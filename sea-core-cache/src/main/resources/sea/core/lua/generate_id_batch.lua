local array = {};
local prefix = 'sea:seq:';
local start = 10000;

local tag = KEYS[1];
local size= tonumber(KEYS[2])
local step = tonumber(KEYS[3])
local key=prefix..tag

local value = redis.call('GET',key)
if value == false then
    array[1]= start;
    redis.call('SET',key, start)
else
    array[1]= tonumber(value)
end

array[2]= redis.call('INCRBY',key, step*size)

return array;