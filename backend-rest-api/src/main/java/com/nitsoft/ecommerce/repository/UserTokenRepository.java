package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {

}
