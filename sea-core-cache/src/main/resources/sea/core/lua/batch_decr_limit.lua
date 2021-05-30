local allow=true
local step = tonumber(ARGV[#ARGV-1])
for i=1, #KEYS do
    local key = KEYS[i]
    local limit = tonumber(ARGV[i])
    local count = redis.call('GET', key)
    if count == false then
        count=0
    end
    if limit >= 0 then
        if ( (count-step) < limit ) then
            allow = false
        break
        end
    end
end

if allow == true then
 for i=1, #KEYS do
 redis.call('DECRBY', KEYS[i],step);
 end
end

return allow