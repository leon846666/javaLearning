package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.mapper.UserMapper;
import org.example.model.UserDO;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.service.UserService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author : Yang
 * @Date :  2023/2/20 19:17
 * @Description：
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private NotifyServiceImpl notifyService;
    private UserMapper userMapper;

    @Override
    public JsonData register(UserRegisterRequest userRegisterRequest) {

        boolean checkCode = false;
        if (StringUtils.isNotBlank(userRegisterRequest.getEmail())) {
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, userRegisterRequest.getName(), userRegisterRequest.getCode());
        }
        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userRegisterRequest, userDO);
        userDO.setCreateTime(new Date());

        //生成秘钥
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        //密码 + 加盐处理
        String cryptPwd = Md5Crypt.md5Crypt(userRegisterRequest.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);
        // 账号唯一性检查 todo
        if (checkUnique(userRegisterRequest.getEmail())) {
            int insert = userMapper.insert(userDO);
            log.info("rows :{},注册成功：{}", insert, userDO);
            // 新用户注册成功，初始化信息 等
            userRegisterInit(userDO);
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }

    }

    /**
     * @Description: user login logic
     *   1. check if the email exists.
     *   2. if yes, get the secret and compare with  pwd.
     * @Author: Yang
     * @Date: 2023/2/22 13:31
     */
    @Override
    public JsonData login(UserLoginRequest userLoginRequest) {
        QueryWrapper queryWrapper = new QueryWrapper<UserDO>().eq("email", userLoginRequest.getEmail());
        ;
        List<UserDO> list = userMapper.selectList(queryWrapper);

        if (list.size() > 0) {
            // 存在
            UserDO userDO = list.stream().findFirst().get();
            String secret = userDO.getSecret();
            String md5Crypt = Md5Crypt.md5Crypt(userLoginRequest.getPwd().getBytes(), secret);
            if (md5Crypt.equals(userDO.getPwd())) {
                return JsonData.buildSuccess();
            } else {
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        } else {
            // 不存在
            log.info("账号不存在， 请重试");
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }

    }

    /**
     * @Description:
     * @Author: Yang
     * @Date: 2023/2/20 19:39
     */
    private boolean checkUnique(String email) {
        QueryWrapper userMapperQueryWrapper = new QueryWrapper<UserDO>();
        userMapperQueryWrapper.eq("email", email);
        List<UserDO> list = userMapper.selectList(userMapperQueryWrapper);
        return list.size() > 0?false :true;
    }


    /**
     * @Description:
     * @Author: Yang
     * @Date: 2023/2/20 19:39
     */
    public void userRegisterInit(UserDO userDO) {

    }
}
