package org.example.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.CouponCategoryEnum;
import org.example.service.CouponService;
import org.example.utils.JsonData;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/api/coupon/v1/")
@AllArgsConstructor
@Slf4j
public class CouponController {
    private CouponService couponService;

    private RedissonClient redissonClient;


    @ApiOperation("分页查询优惠券")
    @GetMapping("pageCoupon")
    public JsonData pageCouponList(
            @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "每页显示多少条") @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        Map<String, Object> pageMap = couponService.pageCouponActivity(page, size);
        return JsonData.buildSuccess(pageMap);
    }

    @ApiOperation("领取优惠券")
    @GetMapping("/add/promotionCoupon/{coupon_id}")
    public JsonData addPromotionCoupon(@ApiParam( value = "优惠卷id" ,required = true)@PathVariable("coupon_id") long couponId) {
        return couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION);
    }

//
//    @ApiOperation("测试分布式事务锁")
//    @GetMapping("/lock")
//    public JsonData lock() {
//        RLock lock = redissonClient.getLock("lock:coupon:1");
//        lock.lock();
//        try {
//            log.info("加锁成功，处理业务逻辑");
//            TimeUnit.SECONDS.sleep(20);
//
//        }catch (Exception e){
//
//        }finally {
//            log.info("解锁成功，其他线程可以进去");
//            lock.unlock();
//        }
//        return JsonData.buildSuccess();
//
//    }

}

