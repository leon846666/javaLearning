package org.example.service;

import org.example.enums.SendCodeEnum;
import org.example.utils.JsonData;

/**
*@Description:
*@Author: Yang
*@Date: 2023/2/8 9:24
*/
public interface NotifyService {

     JsonData sendCode(SendCodeEnum sendCodeType, String to);
}
