/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';

angular.module('marketplace')

.constant('AppConstant', {
    DEFAULT_MIN_PRICE_RANGE: 0,
    DEFAULT_MAX_PRICE_RANGE: 999,
    pathFile: "",  //"http://localhost:8080/ecommerce-rest-api/upload/",
    SESSION_COOKIES: "AccessToken"
})

.constant('api', {
    REGISTER: {name: 'users/register', type: 'POST'},
    LOGIN: {name: 'users/login', type: 'POST'},  
    LOGOUT: {name: 'api/logout', type: 'POST', token: true},
    GET_USER_PROFILE: {name: '/auth/session/data', type: 'GET'}
})

// Define error code
.constant('error', {
    AUTH: [
        {code: 120, desc: 'The token is expired'},
        {code: 121, desc: "The token doesn't exist"},
        // { code: 111, desc: "User name or password is not correct" }, // Handle in Login page
        {code: 110, desc: "User does not exist"}
    ],
    // Define more error code
    OTHERS: [
        {code: 11, desc: "You don't have enough permission to do this action!"},
        {code: 101, desc: "The App with id given does not exist"}
    ]
});