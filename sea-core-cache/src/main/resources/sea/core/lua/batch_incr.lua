local step = ARGV[1]
if step== false then
   step=1
end
for i=1, #KEYS do
 redis.call('INCRBY',KEYS[i],step);
end
return true