package org.example.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.enums.BizCodeEnum;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.service.FileService;
import org.example.service.UserService;
import org.example.utils.JsonData;
import org.example.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
    private UserService userService;

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


    /**
     * 用户注册
     * @param
     * @return
     */
    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public JsonData register(@ApiParam(value = "用户注册请求",required = true) @RequestBody UserRegisterRequest userRegisterRequest){
        JsonData register = userService.register(userRegisterRequest);
        return register;
    }

    /**
     * 用户登录
     * @param
     * @return
     */
    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public JsonData login(@ApiParam(value = "用户登录请求",required = true) @RequestBody UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }

    /**
     * 刷新token
     * @param
     * @return
     */
    @ApiOperation("刷新token")
    @PostMapping(value = "/refreshToken")
    public JsonData refreshToken(@ApiParam(value = "刷新token",required = true) @RequestBody Map<String ,String > requestMap){
        return userService.refreshToken(requestMap);
    }




    /**
     * 个人信息查询
     * @param
     * @return
     */
    @ApiOperation("个人信息查询")
    @GetMapping(value = "/getUserDetail")
    public JsonData getUserDetail(){
        UserVO vo=userService.getUserDetail();
        return JsonData.buildSuccess(vo);
    }





}

