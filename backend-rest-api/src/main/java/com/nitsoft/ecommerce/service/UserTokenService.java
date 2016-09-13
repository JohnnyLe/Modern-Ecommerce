package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken save(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }

}
