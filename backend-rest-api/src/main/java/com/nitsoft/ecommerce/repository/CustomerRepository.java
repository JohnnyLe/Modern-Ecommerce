
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository  extends CrudRepository<User, String>{
    
}
