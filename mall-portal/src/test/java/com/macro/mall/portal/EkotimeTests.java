package com.macro.mall.portal;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.PromotionProduct;
import com.macro.mall.portal.service.OmsPortalOrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by macro on 2018/8/27.
 * 前台商品查询逻辑单元测试
 */
@SpringBootTest
public class EkotimeTests {
    @Autowired
    private OmsPortalOrderService portalOrderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Test
    public void testGetPromotionProductList(){
    	//Object openorderList = portalOrderService.openorderList("data_fram_info1");
    	//Object openorderList=redisService.get("data_fram_info1");
    	
    	//List<Object> records=redisService.lRange("data_fram_info1", 0, 1);
    	
    	//List<Object> records = (List<Object>) redisTemplate.opsForValue().get("data_fram_info1");
    	
    	Object records = stringRedisTemplate.opsForValue().get("data_fram_info1");
    	
        System.out.println(records);
    	CommonResult.success(records);
    }
    
}
