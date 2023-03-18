package org.example.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Yang
 * @Date :  2023/3/17 17:41
 * @Description：
 */
@Data
public class NewUserCouponRequest {

    @ApiModelProperty(name = "用户Id",value = "1")
    @JsonProperty("user_id")
    private long userId;

    @ApiModelProperty(name = "用户名",value = "abc")
    @JsonProperty("name")
    private String name;
}
