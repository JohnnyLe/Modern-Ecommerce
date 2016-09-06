/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nitsoft.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author tmhao
 */
@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }
    
    
    @RequestMapping(value = "SuperAdmin", method = RequestMethod.GET)
    public String signup() {
        return "admin";
    }
}
