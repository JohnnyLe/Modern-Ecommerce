package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository customerRepository;
    
    public User getUserByEmail(String email, long companyId) {
        return customerRepository.findByEmail(email, companyId);
    }

    public User save(User users) {
        return customerRepository.save(users);
    }
}
