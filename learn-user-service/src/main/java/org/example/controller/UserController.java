package org.example.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.BizCodeEnum;
import org.example.service.FileService;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangLiu
 * @since 2022-12-17
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
@AllArgsConstructor
public class UserController {

    private FileService fileService;

    /**
     * 上传用户头像
     * 默认文件大小 1M,超过会报错
     * @param file
     * @return
     */
    @ApiOperation("用户头像上传")
    @PostMapping(value = "/upload")
    public JsonData uploadHeaderImg(@ApiParam(value = "文件上传",required = true) @RequestPart("file") MultipartFile file){

        String result = fileService.uploadUserImage(file);

        return result != null?JsonData.buildSuccess(result):JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);

    }
}

