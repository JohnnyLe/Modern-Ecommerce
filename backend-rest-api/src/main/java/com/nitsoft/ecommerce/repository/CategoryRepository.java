package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * public interface PacketRepository extends CrudRepository<Packet, Long> {}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    
//    @Query("SELECT pad FROM Category pad WHERE pad.companyId = :companyId")
    public Category findByCategoryId(@Param("categoryId") Long categoryId);
    
    Iterable<Category> findByCompanyId(@Param("companyId") Long companyId);
    
}
