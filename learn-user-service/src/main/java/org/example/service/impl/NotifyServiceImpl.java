package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.service.MailService;
import org.example.service.NotifyService;
import org.example.utils.CheckUtil;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.stereotype.Service;

/**
 * @Author : Yang
 * @Date :  2023/2/8 9:28
 * @Description：
 */
@Service
@Slf4j
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private MailService mailService;
    private static final String SUBJECT = "A subject";
    private static final String CONTENT = "This is content";
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {

        if(CheckUtil.isEmail(to)){
            //邮箱验证码
            String code = CommonUtil.getRandomCode(6);
            mailService.sendEmail(to,SUBJECT,String.format(CONTENT,code));
            return JsonData.buildSuccess();

        }else if(CheckUtil.isPhone(to)){
            //短信验证码
        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }
}
