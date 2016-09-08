/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

/**
 */
public class APIName {

    // version
    public static final String VERSION = "api/";

    // charset
    public static final String CHARSET = "application/json;charset=utf-8";

    // action user
    public static final String LOGIN = VERSION + "user/login";
    public static final String LOGOUT = VERSION + "user/logout";

    // categories
    public static final String PRODUCTS = VERSION + "products";
	public static final String PRODUCTS_BY_CATEGORY = PRODUCTS + "/category";
    public static final String GET_LIST = VERSION + "category/list";

    // SSO
    public static final String OAUTH_LOGIN = VERSION + "oauth/login";
    public static final String OAUTH_IMPLICIT_LOGIN = VERSION + "oauth/implicit/login";
    public static final String CHECK_API_KEY = VERSION + "oauth";
    //Orders
    public static final String ADD_ORDERS = VERSION + "orders/add";
    public static final String GETALL_ORDERS = VERSION + "orders/list";
    public static final String DELETE_OEDERS = VERSION + "orders/delete";
}
