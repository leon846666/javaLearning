package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.component.MailService;
import org.example.constant.CacheKey;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.service.NotifyService;
import org.example.utils.CheckUtil;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private static final String CONTENT = "This is your validation code %s, don't tell anyone.";
    private RedisTemplate redisTemplate;
    private static final int CODE_EXPIRE = 60 * 1000;



    @Override

    /**
     * @Description: checking if sending a txt in a frequency
     * @Author: Yang
     * @Date: 2023/2/18 17:22
     */

    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {
        redisTemplate = CommonUtil.setRedisTemplate(redisTemplate);
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeType.name(), to);
        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);

        //如果不为空，判断是否60秒内重复发送
        if (Objects.nonNull(cacheValue)) {
            //如果当前时间戳 减去 验证码发送时间戳 小于60秒，则不给重复发送
            long ttl = Long.parseLong(cacheValue.toString().split("_")[1]);
            long l = CommonUtil.getCurrentTimeStamp() - ttl;
            if (l <= 1000 * 60) {
                log.info("重复发送验证码，时间间隔 {} 秒", l / 1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }
        String code = CommonUtil.getRandomCode(6);
        String value = code + "_" + CommonUtil.getCurrentTimeStamp();
        redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRE, TimeUnit.MILLISECONDS);

        if (CheckUtil.isEmail(to)) {
            //邮箱验证码
            mailService.sendEmail(to, SUBJECT, String.format(CONTENT, code));
            return JsonData.buildSuccess();

        } else if (CheckUtil.isPhone(to)) {
            //短信验证码
        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    /**
     * @Description:
     * @Author: Yang
     * @Date: 2023/2/20 16:50
     */
    public boolean checkCode(SendCodeEnum sendCodeEnum,String to,String code){
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);

        Object o = redisTemplate.opsForValue().get(cacheKey);
       if(Objects.nonNull(o)){
            String cacheCode = o.toString().split("_")[0];
            if(cacheCode.equals(code)){
                //删除验证码
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
