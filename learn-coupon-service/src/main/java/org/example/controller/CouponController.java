package org.example.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.CouponCategoryEnum;
import org.example.service.CouponService;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
public class CouponController {
    private CouponService couponService;


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
}

