package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.hmdp.utils.RedisConstants.CACHE_SHOPTYPE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<ShopType> queryTypeList() {
        // 1.是否命中redis
        String key = CACHE_SHOPTYPE_KEY;
        ListOperations<String, String> ops = stringRedisTemplate.opsForList();
        List<String> list = ops.range(key, 0, ops.size(key) - 1);

        List<ShopType> shopTypeList = new LinkedList<>();
        // 2.存在，直接返回
        if (!list.isEmpty()) {
            for (String shopTypeStr : list) {
                ShopType shopType = JSONUtil.toBean(shopTypeStr, ShopType.class);
                shopTypeList.add(shopType);
            }
            return shopTypeList;
        }
        // 3.不存在，从数据库查询
        shopTypeList = list();
        // 4.查不到，返回错误信息
        if (shopTypeList == null) {
            return null;
        }
        // 5.查到了，存进redis
        for (ShopType shopType : shopTypeList) {
            String str = JSONUtil.toJsonStr(shopType);
            list.add(str);
        }
        stringRedisTemplate.opsForList().leftPushAll(key, list);
        // 6.返回给客户端
        return shopTypeList;
    }
}
