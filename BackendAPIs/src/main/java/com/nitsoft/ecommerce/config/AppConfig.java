/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author lxanh
 */
 @Configuration
 @PropertySource("classpath:/app.properties")
 @EnableAspectJAutoProxy
 public class AppConfig {
     @Autowired
     Environment env;
     public String getValueOfKey(String key) {
         return env.getProperty(key);
     }
 }
