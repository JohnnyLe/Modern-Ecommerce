/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Role;
import javax.transaction.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Quy Duong
 */
@Repository
@Transactional
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {
    
}
