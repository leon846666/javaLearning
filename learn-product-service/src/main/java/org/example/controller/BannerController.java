package org.example.controller;


import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.example.service.BannerService;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-18
 */
@RestController
@RequestMapping("/api/product/v1/banner")
@AllArgsConstructor
public class BannerController {

    private BannerService bannerService;

    @ApiOperation("轮播图列表")
    @GetMapping("/list")
    public JsonData list(){
        return bannerService.list();
    }


}

