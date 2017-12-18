package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    
   User findByEmailAndCompanyIdAndStatus (String email, Long companyid, int status);
   User findByUserIdAndCompanyIdAndStatus (String userId, Long companyid, int status);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.companyId = :companyId")
    User findByEmail(@Param("email") String email, @Param("companyId") long companyId);
}
