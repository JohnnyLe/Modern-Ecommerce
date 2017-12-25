package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepository extends CrudRepository<UserAddress, String> {
    UserAddress findByUserIdAndStatus(String userId, int status);
}
