package org.example.interceptor;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.enums.BizCodeEnum;
import org.example.model.LoginUser;
import org.example.utils.CommonUtil;
import org.example.utils.JWTUtil;
import org.example.utils.JsonData;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : Yang
 * @Date :  2023/3/3 13:50
 * @Description：
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String accessToken = request.getHeader("token");
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }

            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtil.checkJWT(accessToken);
                if (claims == null) {
                    //告诉登录过期，重新登录
                    CommonUtil.sendJsonMessage(response, JsonData.buildError("登录过期，重新登录"));
                    return false;
                }

                Long id = Long.valueOf(claims.get("id").toString());
                String headImg = (String) claims.get("head_img");
                String mail = (String) claims.get("mail");
                String name = (String) claims.get("name");

                // 用户信息传递
                LoginUser loginUser =LoginUser.builder()
                        .name(name)
                        .headImg(headImg)
                        .id(id)
                        .email(mail)
                        .build();
                // classic way
                //request.setAttribute("loginUser",loginUser);

                // use ThreadLocal
                /**
                 * Each thread holds an implicit reference to its copy of a thread-local variable as long as the
                 * thread is alive and the ThreadLocal instance is accessible
                 */
                threadLocal.set(loginUser);

                return true;

            }

        } catch (Exception e) {
            log.error("拦截器错误:{}", e);
        }

        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;

    }

}
