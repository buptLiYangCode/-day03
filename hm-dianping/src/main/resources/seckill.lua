
local voucherId = ARGV[1]

local userId = ARGV[2]
-- 库存key
local stockKey = 'seckill:stock:' .. voucherId
-- 订单key
local orderKey = 'seckill:order:' .. voucherId

-- 3 脚本业务
-- 3.1 判断库存是否充足
if(tonumber(redis.call('get', stockKey)) <= 0) then
    -- 库存不足，返回1
    return 1;
end
--3.2 判断用户是否下单
if(redis.call('sismember', orderKey, userId) == 1) then
    -- 已存在，是重复下单
    return 2
end
--3.3 第一次购买，扣减库存
redis.call('incrby', stockKey, -1)
--3.4 下单
redis.call('sadd', orderKey, userId)

return 0





