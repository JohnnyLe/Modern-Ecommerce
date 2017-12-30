// I put all constant value here
// so that everybody can go here to modify the constant value if any changes
angular.module('ec-admin.constant', [])

// App config
    .constant("AppConfig", {

        // Cache module using ocLazyLoad
        OCLAZY_CACHE_MODULE: true, // Should use "true" when deploy to server

        API_PATH: 'http://localhost:8080/ecommerce-rest-api/api/v1/1',
        PATH_FILE: "http://localhost:8080/ecommerce-rest-api/upload/",
        // API_PATH: 'http://localhost:8080/api/v1/1',
        // PATH_FILE: "http://localhost:8080/upload/",
        SESSION_COOKIES: 'AccessToken'

    })

    // patterns
    .constant("Patterns", {

        EMAIL_PATTERN: /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/,
        USERNAME_PATTERN: /^[a-z0-9_-]{3,45}$/,
        NAME_PATTERN: /^[a-zA-Z ]{3,45}$/,
        PHONE_PATTERN: /^\+?[0-9]\d{1,16}$/,
        PASSWORD_PATTERN: /^.{6,}$/,
        APP_NAME_PATTERN: /^[a-zA-Z0-9 _-]{3,45}$/,
        DOMAIN_PATTERN: /^[a-zA-Z0-9][a-zA-Z0-9-_]{0,61}[a-zA-Z0-9]{0,1}\.([a-zA-Z]{1,6}|[a-zA-Z0-9-]{1,30}\.[a-zA-Z]{2,3})$/,
        SERVER_KEY_PATTERN: /^[a-zA-Z0-9]{0,255}$/,
        URL_PATTERN: /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/,
        ROLE_NAME_PATTERN: /^[A-Za-z0-9]{3,45}$/,
        ROLE_DESC_PATTERN: /^[\s\w.,-:]{0,255}$/

    })

    // APIs
    .constant('API', {

        LOGOUT: {path: '/auth/logout'},
        CONSOLE_LOGIN: {path: '/auth/admin/login'},
        CONSOLE_REGISTER: {path: '/users/console/register'},
        GET_USER_PROFILE: {path: '/auth/session/data', method: 'GET'},
        GET_LIST_PRODUCT: {path: '/products/filter', method: 'POST'},
        CREATE_PRODUCT: {path: '/products/create', method: 'POST'},
        UPDATE_PRODUCT: {path: '/products/update', method: 'POST'},
        DETAIL_PRODUCT: {path: '/products/detail/', method: 'POST'},
        DELETE_PRODUCT: {path: '/products/delete/', method: 'POST'},
        LIST_CATEGORY: {path: '/categories/list', method: 'GET'},
        CREATE_CATEGORY: {path: '/categories/create', method: 'POST'},
        UPDATE_CATEGORY: {path: '/categories/update', method: 'POST'},
        DETAIL_CATEGORY: {path: '/categories/detail/', method: 'GET'},
        DELETE_CATEGORY: {path: '/categories/delete', method: 'POST'},
        GET_ORDER_BY_COMPANY: {path: '/orders/listOrder', method: 'POST'},
        DETAIL_ORDERS: {path: '/orders/detail/', method: 'POST'},
        CHANGE_STATUS_ORDERS: {path: '/orders/delete/', method: 'POST'},
        REGISTER_USER: {path: '/users/register', method: 'POST'},
        GET_USER_LIST: {path: '/users/list', method: 'POST'},
        DELETE_USER: {path: '/users/delete', method: 'POST'},
        GET_USER_INFO: {path: '/users/detail/', method: 'POST'},
        EDIT_USER: {path: '/users/update', method: 'POST'},
        CHANGE_PASSWORD: {path: '/users/changePassword', method: 'POST'}
    })

    // API status
    .constant('APIStatus', [

        {status: 200, mgsKey: 'api.status.200', message: "OK"},
        //////////////////
        // CLIENT SIDE  //
        //////////////////
        {status: 400, mgsKey: 'api.status.400', message: "Bad request"},
        {status: 401, mgsKey: 'api.status.404', message: "Unauthorized or Access Token is expired"},
        {status: 403, mgsKey: 'api.status.405', message: "Forbidden! Access denied"},
        {status: 406, mgsKey: 'api.status.406', message: "Bad parameters"},
        //////////////////
        // SERVER SIDE  //
        //////////////////
        {status: 500, mgsKey: 'api.status.500', message: "Internal Server Error"},
        {status: 501, mgsKey: 'api.status.501', message: "Create model error"},
        //////////////////
        // SESSION SIDE //
        //////////////////
        {status: 600, mgsKey: 'api.status.600', message: "Access token not found"},
        {status: 601, mgsKey: 'api.status.601', message: "Access token is invalid"},
        {status: 602, mgsKey: 'api.status.602', message: "Access token is expired"}

    ])

    .constant('moment', window.moment);