package org.example.enums;

import lombok.Getter;

public enum BizCodeEnum {

    /**
     * 通⽤操作码
     */
    OPS_REPEAT(110001,"重复操作"),
    /**
     *验证码
     */
    CODE_TO_ERROR(240001,"接收号码不合规"),
    CODE_LIMITED(240002,"验证码发送过快"),
     // 接⼝统⼀协议 JsonData⼯具类开发
    CODE_ERROR(240003,"验证码错误"),
    CODE_CAPTCHA(240101,"图形验证码错误"),
    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001,"账号已经存在"),
    ACCOUNT_UNREGISTER(250002,"账号不存在"),
    ACCOUNT_PWD_ERROR(250003,"账号或者密码错误"),
    ACCOUNT_UNLOGIN(250004,"账号未登录" ),
    /**
     *  文件上传
     */
    FILE_UPLOAD_USER_IMG_FAIL(600101,"用户上传头像失败"),

    /**
     *  刷新token
     */
    TOKEN_REFRESH_NOT_EXIST(260001,"刷新令牌不存在"),

    /**
     * 访问令牌
     */
    ACCESS_REFRESH_NOT_EXIST(260001,"访问令牌不存在");

    @Getter
    private String message;

    @Getter
    private int code;

    private  BizCodeEnum(int code ,String message){
        this.code = code;
        this.message  = message;
    }



}
