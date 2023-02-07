package org.example.service;

/**
*@Description:
*@Author: Yang
*@Date: 2023/2/7
*/
public interface MailService {

    void sendEmail(String to,String subject,String content);
}
