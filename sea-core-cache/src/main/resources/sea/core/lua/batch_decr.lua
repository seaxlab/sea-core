local step = ARGV[1]
if step== false then
   step=1
end
for i=1, #KEYS do
 redis.call('DECRBY', KEYS[i], tonumber(step));
end
return true