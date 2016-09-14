package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.UserRepository;
import com.nitsoft.ecommerce.repository.UserTokenRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    public User getUserByEmail(String email, long companyId) {
        return userRepository.findByEmail(email, companyId);
    }

    public User save(User users) {
        return userRepository.save(users);
    }

    public User getUserById(String userId) {
        return userRepository.findOne(userId);
    }

    public User getUserByActivationCode(String token) {
        UserToken userToken = userTokenRepository.findOne(token);

        if (userToken != null) {
            return userRepository.findOne(userToken.getUserId());
        } else {
            return null;
        }
    }
}
