package org.example.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Yang
 * @Date :  2023/2/20 17:03
 * @Description：
 */
@Data
@ApiModel(value = "用户注册对象",description = "用户注册请求对象")
public class UserRegisterRequest {

    @ApiModelProperty(value = "昵称",example = "liu")
    private String name;

    @ApiModelProperty(value = "密码",example = "12345")
    private String pwd;

    @ApiModelProperty(value = "头像",example = "abc.jpg")
    private String headImg;

    @ApiModelProperty(value = "签名",example = "你好我好")
    private String slogan;

    @ApiModelProperty(value = "邮箱",example = "email@123.com")
    private String email;

    @ApiModelProperty(value = "验证码",example = "code")
    private String code;

}
