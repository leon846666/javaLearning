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
@ApiModel(value = "用户登录对象",description = "用户登录请求对象")
public class UserLoginRequest {

    @ApiModelProperty(value = "邮箱",example = "email@123.com")
    private String email;

    @ApiModelProperty(value = "密码",example = "12345")
    private String pwd;

}
