package org.example.service;

import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.utils.JsonData;

/**
 * @Author : Yang
 * @Date :  2023/2/20 16:59
 * @Description：
 */
public interface UserService {

    JsonData register(UserRegisterRequest userRegisterRequest);

    JsonData login(UserLoginRequest userLoginRequest);
}
