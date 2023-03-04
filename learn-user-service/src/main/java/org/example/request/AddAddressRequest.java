package org.example.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : Yang
 * @Date :  2023/2/20 17:03
 * @Description：
 */
@Data
@ApiModel(value = "新增收获地址请求",description = "新增收获地址请 求")
public class AddAddressRequest {


    /**
     * 是否默认收货地址：0->否；1->是
     */
    @ApiModelProperty(value = "是否是默认收获地址，0-否，1-是",example = "0")
    @JsonProperty("default_status")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @ApiModelProperty(value = "收发货人姓名，0-否，1-是",example = "123123")
    @JsonProperty("receive_name")
    private String receiveName;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "电话",example = "17674841234")
    private String phone;

    /**
     * 省/直辖市
     */
    @ApiModelProperty(value = "省",example = "Guangdong")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市",example = "Shenzhen")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区",example = "天河区")
    private String region;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址",example = "龙华区龙华小区111号4栋22号")
    @JsonProperty("detail_address")
    private String detailAddress;

}
