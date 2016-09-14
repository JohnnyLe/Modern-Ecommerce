package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.UserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
    @Query("SELECT u FROM UserToken u WHERE u.userId = :userId AND u.companyId = :companyId")
    UserToken findExpirationDate(@Param("userId") String userId, @Param("companyId") long companyId);

}
