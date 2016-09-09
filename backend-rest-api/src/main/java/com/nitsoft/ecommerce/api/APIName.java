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

    // product api links
    public static final String PRODUCTS = VERSION + "products";
    public static final String PRODUCTS_BY_CATEGORY = PRODUCTS + "/category";
    public static final String PRODUCTS_FILTER_LIST = PRODUCTS + "/filter";

    // categories api links
    public static final String GET_LIST = VERSION + "category/list";

    //company api link
    public static final String COMPANIES = VERSION + "companies";
    public static final String COMPANIES_SEARCH_BY_ID = COMPANIES + "/{id}";

    //review api link
    public static final String REVIEWS = VERSION + "reviews";
    public static final String REVIEWS_BY_PRODUCT_ID = "/{id}";
    // SSO
    public static final String OAUTH_LOGIN = VERSION + "oauth/login";
    public static final String OAUTH_IMPLICIT_LOGIN = VERSION + "oauth/implicit/login";
    public static final String CHECK_API_KEY = VERSION + "oauth";
    //Category
    public static final String CATEGORIES = VERSION + "categories";
    public static final String CATEGORIES_ID = VERSION + "categories/{id}";
}
