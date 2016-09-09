/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoftware.ecommerce.repository;

/**
 *
 * @author NHU LINH
 */
import com.nitsoft.ecommerce.model.Category;
import org.springframework.data.repository.CrudRepository;

import com.reus.model.User;

/* 当使用非关系型数据库mongodb时要使用下面这个接口
 * 同时pom文件中还应该有mongodb的驱动包说spring-boot-starter-data-mongodb
 * public interface UserRepository extends MongoRepository<User, Long> {}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    
}
