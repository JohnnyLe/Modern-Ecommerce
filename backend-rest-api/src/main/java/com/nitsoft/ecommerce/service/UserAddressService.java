package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.repository.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    public UserAddress getAddressByUserIdAndStatus(String userId, int status) {
        return userAddressRepository.findByUserIdAndStatus(userId, status);
    }

}
