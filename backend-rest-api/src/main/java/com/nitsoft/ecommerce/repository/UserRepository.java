package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
