package org.example.service;

import org.example.model.AddressDO;
import org.example.request.AddAddressRequest;
import org.example.utils.JsonData;

public interface AddressService {

    AddressDO detail(long id);

    int addAddress(AddAddressRequest addressRequest);
}
