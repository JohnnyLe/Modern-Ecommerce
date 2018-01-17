'use strict';

// Session module. Provide session factory $sessionFactory
angular.module('ec-admin.session', ['ngCookies', 'ec-admin.utils', 'ec-admin.constant'])

// Session
.factory('Session', [

    '$cookies',
    'AppConfig',
    'API',
    'APIStatus',
    'toastr',
    'Util',
    '$state',
    '$q',

    function ($cookies, AppConfig, API, APIStatus, toastr, Util, $state, $q) {

        // store current user
        var sessionUser;
        
        var service = {

            // Get token from cookie
            getAccessToken: function () {
                return $cookies.get(AppConfig.SESSION_COOKIES);
            },

            // Set token
            setAccessToken: function (token) {

                if (token) {
                    $cookies.put(AppConfig.SESSION_COOKIES, token);
                }
            },

            // Get user
            getUser: function () {
                return sessionUser;
            },

            // Set user
            setUser: function (user) {

                // Set user
                if (angular.isObject(user)) {
                    sessionUser = user;
                }
            },

            consoleLogin: function (data, callback) {
                
                var request = Util.createRequest(API.CONSOLE_LOGIN, data, function(response) {
                    
                    callback && callback(response);
                    var status = response.status;
                    if (status === 200) {
                        // store user into session
                        var accessToken = response.data;
                        service.setAccessToken(accessToken);
                    }
                });

                return request;
            },

            logout: function (callback) {

                var request = Util.createRequest(API.LOGOUT, function(response) {
                    
                    callback && callback(response);
                    
                }).finally(function () {
                    // clear user into session
                    $cookies.remove(AppConfig.SESSION_COOKIES);
                    // redirect to login page
                    $state.go('login');
                });

                return request;
            },

            detectBrowserLang: function () {
                // Language code: http://4umi.com/web/html/languagecodes.php || http://msdn.microsoft.com/en-us/library/ie/ms533052.aspx
                var lang = window.navigator.browserLanguage || window.navigator.language;
                var language = lang.substr(0, 2);
                switch (language) {
                    case 'en':
                    case 'ja':
                        return language;
                        break;
                    default:
                        return 'en';
                        break;
                }
            },

            init: function () {
                
                var defer = $q.defer();
                console.log("init");
                // validate session
                if (service.getAccessToken()) {
                    
                    // get user info
                    Util.createRequest(API.GET_USER_PROFILE, {}, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            
                            // store user into session
                            var userProfile = response.data;
                            service.setUser(userProfile);
                            defer.resolve(userProfile);
                        } else {
                            
                            // clear user into session
                            $cookies.remove(AppConfig.SESSION_COOKIES);
                            // show error
                            var err = _.find(APIStatus, {status: status});
                            if (err) {
                                toastr.error($i18next(err.mgsKey));
                            }
                            defer.reject();
                        }
                        
                    }, function(errResponse) {
                        
                        // clear user into session
                        $cookies.remove(AppConfig.SESSION_COOKIES);
                        defer.reject(errResponse);

                    }, true);
                    
                } else {
                    
                    console.info('session redirect page');
                    // redirect to login page
                    $state.go('login');
                    defer.reject();
                }
                
                return defer.promise;
            }
        };

        return service;
    }
]);