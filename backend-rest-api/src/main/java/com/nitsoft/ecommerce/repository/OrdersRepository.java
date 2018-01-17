/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Louis Duong
 */
public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

    @Query("SELECT pad FROM Orders pad WHERE pad.companyId = :companyId")
    Page<Orders> findAllByCompanyId(@Param("companyId") long companyId, Pageable pageable);
    
    Orders findOneByIdAndCompanyId(Long orderId, Long companyId);
    
}
