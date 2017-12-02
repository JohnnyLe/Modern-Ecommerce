/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';

angular.module( 'marketplace' )

.constant( 'server',  {

    // Local
    URL: 'http://localhost:8080/ecommerce-rest-api/api/v1/1'
    // Server
    //URL: 'http://52.196.33.166/marketplace-rest-api'
})

// Hold app info
.constant( 'app', {
    EMAIL_REGEX: '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}',
    COOKIE_NAME: 'authen_token', // Token name
    PAGE_LENGTH: 10 // Length per page using for list item
    
});