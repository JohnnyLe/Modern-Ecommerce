package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Query("SELECT c FROM Company c")
    Iterable<Company> findAll(long companyId);

    @Query("SELECT c FROM Company c WHERE c.companyId = :companyId")
    Company findByCompanyId(@Param("companyId") long companyId);
    
}
