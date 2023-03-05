package org.example.service;

import org.example.model.AddressDO;
import org.example.request.AddAddressRequest;
import org.example.utils.JsonData;
import org.example.vo.AddressVO;

import java.util.List;

public interface AddressService {

    AddressVO detail(long id);

    int addAddress(AddAddressRequest addressRequest);

    int deleteAddress(long id);

    List<AddressVO> getAllUserAddress();
}
