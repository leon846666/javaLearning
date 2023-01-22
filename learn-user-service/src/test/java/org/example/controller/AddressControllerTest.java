package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.UserApplication;
import org.example.model.AddressDO;
import org.example.service.AddressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AddressControllerTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void testAddressDetails(){
        AddressDO detail = addressService.detail(2L);
        log.info(detail.toString());
        Assert.assertNotNull(detail);

    }
}