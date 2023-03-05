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
import org.example.vo.AddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private AddressMapper addressMapper;

    @Override
    public AddressVO detail(long id) {
        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));
        if(Objects.isNull(addressDO)){
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO,addressVO);
        return addressVO;
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

    @Override
    public int deleteAddress(long id) {
        QueryWrapper queryWrapper = new QueryWrapper<AddressDO>().eq("id",id);
        int delete = addressMapper.delete(queryWrapper);
        return delete;
    }

    @Override
    public List<AddressVO> getAllUserAddress(long id) {
        QueryWrapper queryWrapper = new QueryWrapper<AddressDO>().eq("user_id",id);
        List<AddressDO> list = addressMapper.selectList(queryWrapper);

        List<AddressVO> addressVOS = list.stream().map(obj -> {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(obj, addressVO);
            return addressVO;
        }).collect(Collectors.toList());

        return addressVOS;
    }
}
