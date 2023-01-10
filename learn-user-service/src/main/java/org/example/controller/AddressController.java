package org.example.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.BizCodeEnum;
import org.example.model.AddressDO;
import org.example.service.AddressService;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public Object getById(@ApiParam(value = "地址id",required = true) @PathVariable("id") long id){
        return JsonData.buildSuccess(addressService.detail(id));
    }

}

