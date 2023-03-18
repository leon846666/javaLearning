package org.example.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.BizCodeEnum;
import org.example.request.NewUserCouponRequest;
import org.example.service.CouponRecordService;
import org.example.utils.JsonData;
import org.example.vo.CouponRecordVO;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/api/couponRecord/v1")
@AllArgsConstructor
public class CouponRecordController {

    private CouponRecordService couponRecordService;

    @ApiOperation("分页查询我的优惠券列表")
    @GetMapping("/page")
    public JsonData page(@RequestParam(value = "page",defaultValue = "1")int page,
                         @RequestParam(value = "size",defaultValue = "20")int size){

        Map<String, Object> pageResult = couponRecordService.page(page, size);
        return JsonData.buildSuccess(pageResult);
    }

    /**
     * 查询优惠券记录信息
     * 水平权限攻击：也叫作访问控制攻击,Web应用程序接收到用户请求，修改某条数据时，没有判断数据的所属人，
     * 或者在判断数据所属人时从用户提交的表单参数中获取了userid。
     * 导致攻击者可以自行修改userid修改不属于自己的数据
     * @param recordId
     * @return
     */
    @ApiOperation("查询优惠券记录信息")
    @GetMapping("/detail/{record_id}")
    public JsonData findUserCouponRecordById(@PathVariable("record_id")long recordId ){

        CouponRecordVO couponRecordVO = couponRecordService.findById(recordId);
        return  couponRecordVO == null? JsonData.buildResult(BizCodeEnum.COUPON_NO_EXITS):JsonData.buildSuccess(couponRecordVO);
    }



}

