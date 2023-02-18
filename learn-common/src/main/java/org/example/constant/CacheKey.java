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
}
