package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken save(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }

    public UserToken getTokenById(String token) {
        return userTokenRepository.findOne(token);
    }

    public void invalidateToken(UserToken userToken) {
        userTokenRepository.delete(userToken);
    }

}
