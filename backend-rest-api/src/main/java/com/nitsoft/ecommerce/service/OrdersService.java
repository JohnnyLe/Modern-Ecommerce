/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author NHU LINH
 */
@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    
    public Orders save(Orders orders){
        return ordersRepository.save(orders);
    }
    
    public Page<Orders> findAllByCompanyId(long companyId, int pageNumber, int pageSize) {
        return ordersRepository.findAllByCompanyId(companyId, new PageRequest(pageNumber, pageSize));
    }
}
