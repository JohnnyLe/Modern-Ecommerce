package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * public interface PacketRepository extends CrudRepository<Packet, Long> {}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    public Category findByCategoryId(@Param("categoryId") Long categoryId);

}
