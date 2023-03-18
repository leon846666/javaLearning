package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : Yang
 * @Date :  2023/2/28 14:51
 * @Description：
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private long id;
    private String name;
    @JsonProperty("head_img")
    private String headImg;
    private String email;

}
