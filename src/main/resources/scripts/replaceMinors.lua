local values = redis.call('SMEMBERS', KEYS[1])
local replaced = {}

for _, value in ipairs(values)
do
    replaced = redis.call('SMOVE', KEYS[1], KEYS[2], value)
end
return replaced