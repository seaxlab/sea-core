local prefix = 'sea:seq:';

local start = 10000;

local tag = KEYS[1];
local step = KEYS[2];
local key=prefix..tag
local sequence
if redis.call('GET',key) == false then
	sequence = start;
	redis.call('SET',key,start);
else
	sequence = tonumber(redis.call('INCRBY', key, step))
end

return sequence