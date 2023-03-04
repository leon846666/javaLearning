package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.LoginInterceptor;
import org.example.mapper.AddressMapper;
import org.example.model.AddressDO;
import org.example.model.LoginUser;
import org.example.request.AddAddressRequest;
import org.example.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private AddressMapper addressMapper;

    @Override
    public AddressDO detail(long id) {
        return addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));
    }

    @Override
    public int addAddress(AddAddressRequest addressRequest) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        addressDO.setUserId(loginUser.getId());
        BeanUtils.copyProperties(addressRequest, addressDO);
        //是否有默认收货地址
        if (addressDO.getDefaultStatus() == 1) {
            AddressDO defaultAddressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()).eq("default_status", 1));
            if (defaultAddressDO != null) {
                //修改为非默认地址
                defaultAddressDO.setDefaultStatus(0);
                addressMapper.update(defaultAddressDO, new QueryWrapper<AddressDO>().eq("id", defaultAddressDO.getId()));
            }
        }
        int rows = addressMapper.insert(addressDO);
        log.info("新增收获地址 rows:{} ,data = {}", rows, addressDO);
        return rows;
    }
}
