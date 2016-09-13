package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email, long companyId) {
        return userRepository.findByEmail(email, companyId);
    }

    public User save(User users) {
        return userRepository.save(users);
    }

    public User getUserById(String userId) {
        return userRepository.findOne(userId);
    }
}
