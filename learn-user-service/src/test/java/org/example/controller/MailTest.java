package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.UserApplication;
import org.example.model.AddressDO;
import org.example.service.AddressService;
import org.example.service.MailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail(){
        mailService.sendEmail("123456@qq.com","an email sent by java","hello world!!!");

    }
}