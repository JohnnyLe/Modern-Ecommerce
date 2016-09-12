/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NHU LINH
 */
public interface OrdersRepository extends CrudRepository<Orders, Long> {

    @Query("SELECT pad FROM Orders pad WHERE pad.id = :id")
    Iterable<Orders> findAllByOrdersId(@Param("id") long id);
    
}
