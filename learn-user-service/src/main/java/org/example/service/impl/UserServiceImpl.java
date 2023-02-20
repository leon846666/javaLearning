package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.mapper.UserMapper;
import org.example.model.UserDO;
import org.example.request.UserRegisterRequest;
import org.example.service.UserService;
import org.example.utils.JsonData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        int insert = userMapper.insert(userDO);

        // 设置密码 todo
        // userDO.setPwd(userRegisterRequest.getPwd());

        // 账号唯一性检查 todo
        if(checkUnique(userRegisterRequest.getEmail())){
            log.info("rows :{},注册成功：{}", insert, userDO);
            // 新用户注册成功，初始化信息 等
            userRegisterInit(userDO);
            return JsonData.buildSuccess();
        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }


    }

    /**
     * @Description:
     * @Author: Yang
     * @Date: 2023/2/20 19:39
     */
    private boolean checkUnique(String email) {
        return false;
    }



    /**
     * @Description:
     * @Author: Yang
     * @Date: 2023/2/20 19:39
     */
    public void userRegisterInit(UserDO userDO) {

    }
}
