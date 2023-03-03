package org.example.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : Yang
 * @Date :  2023/3/3 17:14
 * @Description：
 */
@Data
public class UserVO {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像
     */
    @JsonProperty("head_img")
    private String headImg;

    /**
     * 用户签名
     */
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    private Integer sex;

    /**
     * 积分
     */
    private Integer points;

    private Date createTime;

    /**
     * 邮箱
     */
    private String email;

}
