package org.example.constant;

/**
 * @Author : Yang
 * @Date :  2023/2/9 9:47
 * @Description：
 */
public class CacheKey {
    /**
     * 注册验证码，第一个是类型，第二个是接受号码（email,phone)
     *
     */
    public static final String CHECK_CODE_KEY = "code:%s:%s";
    /**
     * 刷新令牌
     */
    public static final String REFRESH_TOKEN_KET = "javaJWTLearn";

    /**
     * 访问令牌
     */
    public static final String ACCESS_TOKEN_KET = "accessToken";


    /**
     * 访问令牌失效时间
     */
    public static final String ACCESS_TOKEN_EXPIRE_TIME = "expireTime";
}
