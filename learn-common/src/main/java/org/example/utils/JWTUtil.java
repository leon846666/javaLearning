package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.CacheKey;
import org.example.model.LoginUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author : Yang
 * @Date :  2023/2/28 14:48
 * @Description：
 */
@Slf4j
@Component
public class JWTUtil {

    private static final String SUBJECT = "javaLearn";
    private static final String SECRET = "liuyangJWTSECRET";
    private static final String TOKEN_PREFIX = "liuyang";
    private static long EXPIRE = 1000*60*5;

    /**
     * 根据用户信息，生成令牌
     *
     * @param user
     * @return
     */
    public static HashMap<String,String> geneJsonWebToken(LoginUser user) {
        HashMap<String ,String > result = new HashMap<>();
        Long userId = user.getId();
        Long expireTime = System.currentTimeMillis() + EXPIRE;
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", userId)
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        result.put(CacheKey.ACCESS_TOKEN_KET,token);
        result.put(CacheKey.ACCESS_TOKEN_EXPIRE_TIME,expireTime.toString());
        String newRefreshToken = CommonUtil.generateUUID();
        result.put(CacheKey.REFRESH_TOKEN_KET, newRefreshToken);
        return result;
    }


    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return  claims;
        }catch (Exception e){
            log.error("解析token出错 {}",e);
            return  null;
        }
    }
}
