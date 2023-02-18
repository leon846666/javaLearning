package org.example.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.service.NotifyService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "通知模块")
@RequestMapping("/api/notify/v1")
@Slf4j
public class NotifyController {

    @Autowired
    private Producer captchaProducer;
    /**
     *临时使用10分钟有效，方便测试
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @ApiOperation("获取图形验证码")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String cacheKey = getCaptchaKey(request);

        String capText = captchaProducer.createText();

        //存储
        redisTemplate.opsForValue().set(cacheKey,capText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "create_date-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            out.close();

        } catch (IOException e) {
            log.error("获取验证码失败:{}",e);
        }
    }

    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String header = request.getHeader("User-Agent");
        log.info("user-agent: {}",header);
        log.info("user-ip: {}",ip);
        String key = "user-service:captcha:" + CommonUtil.MD5(ip + header);
        return key;
    }

    /**
     * 支持手机号、邮箱发送验证码
     * @return
     */
    @ApiOperation("发送验证码")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@ApiParam("收信人") @RequestParam(value = "to", required = true)String to,
                                     @ApiParam("图形验证码") @RequestParam(value = "captcha", required = true)String  captcha,
                                     HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
            JsonData jsonData = notifyService.sendCode(SendCodeEnum.USER_REGISTER,to);
            return jsonData;
        }else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA);
        }

    }


}
