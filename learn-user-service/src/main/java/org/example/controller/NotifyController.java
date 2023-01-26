package org.example.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@Api(tags = "通知模块")
@RequestMapping("/api/notify/v1")
@Slf4j
public class NotifyController {

    @Autowired
    private Producer captchaProducer;


    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaProducerText = captchaProducer.createText();
        log.info(" 图形验证码 :{}", captchaProducerText);
        BufferedImage image = captchaProducer.createImage(captchaProducerText);
        ServletOutputStream out = null;

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("获取图形验证码异常 :{}",e);
        }
    }


}
