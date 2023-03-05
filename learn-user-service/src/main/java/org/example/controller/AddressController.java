package org.example.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.BizCodeEnum;
import org.example.exception.BizException;
import org.example.interceptor.LoginInterceptor;
import org.example.model.LoginUser;
import org.example.request.AddAddressRequest;
import org.example.service.AddressService;
import org.example.utils.JsonData;
import org.example.vo.AddressVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author yangLiu
 * @since 2022-12-17
 */
@Api(tags = "收获地址模块")
@RestController
@RequestMapping("/api/address/v1/")
@AllArgsConstructor
public class AddressController {


    AddressService addressService;

    @ApiOperation("根据id查找收获的地址")
    @GetMapping("find/{id}")
    public JsonData getById(@ApiParam(value = "地址id", required = true) @PathVariable("id") long id) {
        AddressVO addressVO = addressService.detail(id);

        return addressVO==null ? JsonData.buildResult(BizCodeEnum.ADDRESS_NO_EXITS): JsonData.buildSuccess(addressVO);
    }


    @ApiOperation("根据id查找收获的地址")
    @PostMapping("/addAddress")
    public Object addAddress(@ApiParam(value = "新增收获底子好", required = true) @RequestBody AddAddressRequest addressRequest) {

        return JsonData.buildSuccess(addressService.addAddress(addressRequest));
    }

    @ApiOperation("删除收获地址")
    @GetMapping("deleteAddress/{id}")
    public JsonData deleteAddress(@ApiParam(value = "地址id", required = true) @PathVariable("id") long id) {
        int i = addressService.deleteAddress(id);
        if(i>0){
            return JsonData.buildSuccess();
        }

        return JsonData.buildError(BizCodeEnum.ADDRESS_DEL_FAIL.getMessage());
    }


    @ApiOperation("根据id查找收获的地址")
    @GetMapping("getAllUserAddress")
    public JsonData getAllUserAddress() {

        List<AddressVO>  addressVOs = addressService.getAllUserAddress();

        return addressVOs==null ? JsonData.buildResult(BizCodeEnum.ADDRESS_NO_EXITS): JsonData.buildSuccess(addressVOs);
    }


}

