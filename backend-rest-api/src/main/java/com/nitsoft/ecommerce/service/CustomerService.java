
package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
      
    public User save(User users){
        return customerRepository.save(users);
    }
}
