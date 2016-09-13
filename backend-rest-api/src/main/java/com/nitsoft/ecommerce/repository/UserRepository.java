package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByEmail(@Param("email") String email, @Param("companyId") long companyId);

}
