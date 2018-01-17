
'use strict';

angular.module('marketplace.authen', ['ngCookies', 'marketplace'])

//        .factory('user', ['$rootScope', '$location', '$injector', 'util', 'app', '$q', 'api', '$cookieStore', '$timeout', function ($rootScope, $location, $injector, util, app, $q, api, $cookieStore, $timeout) {
//
//                // Create authen info
//                var user = {
//                    authorized: false,
//                    info: {}
//                };
//
//                // Name using for token storage
//                var TOKEN_COOKIE_NAME = app.COOKIE_NAME;
//
//                $rootScope.user = user;
//
//                // Check whether route service exists or not
//                if ($injector.has) {
//
//                    var $route = $injector.has('$route') ? $injector.get('$route') : null;
//                } else {
//
//                    var $route = $injector.get('$route');
//                }
//
//                // Check whether a route is public or not
//                function isPublic(route) {
//
//                    return (route && (route.public === true));
//                }
//
//                // Check route auto redirect inside after authen or not
//                function isRedirect(route) {
//
//                    return (route && (route.redirect === true));
//                }
//
//                // Check access to route
//                var checkAccessToRoute = function (route) {
//
//                    if (route.$$route) {
//
//                        if (isPublic(route.$$route) === false && user.authorized === false) {
//                            service.authenticationRequiredHandler(route);
//                            return false;
//                        }
//                    }
//
//                    return true;
//                };
//
//                // Provide API
//                var service = {
//
//                    // Require handler
//                    authenticationRequiredHandler: function () {
//
//                        // Transition to login page
//                        $timeout(function () {
//
//                            $location.path('/login');
//                        });
//                    },
//
//                    // Success handler
//                    authenticationSuccessHandler: function () {
//
//                        // Default route
//                        $timeout(function () {
//
//                            $location.path('/sniff');
//                        });
//                    },
//
//                    // Get & set token value
//                    token: function (value) {
//
//                        if (value) {
//
//                            // Set value for user object
//                            user.authorized = true;
//                            user.info.userTokens = value;
//                            // Put cookie value
//                            $cookieStore.put(TOKEN_COOKIE_NAME, value);
//
//                            return value;
//                        } else {
//
//                            return $cookieStore.get(TOKEN_COOKIE_NAME);
//                        }
//                    },
//
//                    // Reset user info
//                    reset: function () {
//
//                        // Remove cookie value
//                        $cookieStore.remove(TOKEN_COOKIE_NAME);
//                        // Reset info
//                        user.authorized = false;
//                        user.info = {};
//                    },
//
//                    // Init
//                    init: function () {
//
//                        var authResolver = {
//
//                            auth: function () {
//
//                                // Get token from cookie store
//                                var cToken = $cookieStore.get(TOKEN_COOKIE_NAME);
//
//                                // Private route
//                                if (isPublic($route ? $route.current.$$route : null) === false) {
//
//                                    var deferred = $q.defer();
//
//                                    if (cToken) {
//                                        // Set token
//                                        service.token(cToken);
//                                        // Accept this route
//                                        deferred.resolve();
//                                    } else {
//                                        // Reset user info in case of token is expired
//                                        service.reset();
//                                        // Require authen
//                                        service.authenticationRequiredHandler();
//                                        // Reject this route
//                                        deferred.reject('No token has been stored at cookie');
//                                    }
//
//                                    return deferred.promise;
//
//                                } else {
//
//                                    if (cToken) {
//
//                                        // Auto redirect based on that route's config
//                                        if (isRedirect($route ? $route.current.$$route : null)) {
//                                            service.authenticationSuccessHandler();
//                                        }
//                                    } else {
//                                        // Reset user info. Prevent display user info
//                                        service.reset();
//                                    }
//
//                                    return true;
//                                }
//                            }
//                        };
//
//                        // Set authen resolver for each route
//                        for (var route in $route.routes) {
//                            // Add resolver for user and permissions
//                            $route.routes[route].resolve = ($route.routes[route].resolve || {});
//                            angular.extend($route.routes[route].resolve, authResolver);
//                        }
//
//                        // Prevent route
////          $rootScope.$on('$routeChangeStart', function( ev, current ) {
////              
////              if (!checkAccessToRoute( current ))
////                  ev.preventDefault();
////          });
//                    },
//
//                    // Do register
//                    register: function (credential, callback) {
//                        var that = this;
//                        // Call login
//                        util.callRequest(api.REGISTER.name, api.REGISTER.type, credential).then(function (result) {
//                            // Check result returned
//                            if ('authInfor' in result) {
//                                // Get user info
//                                angular.extend(user.info, result.authInfor);
//                                // Set token
//                                service.token(result.authInfor.userTokens);
//                                // Invoke success authen
//                                that.authenticationSuccessHandler();
//                            } else
//                                callback && callback(result);
//
//                        }, function (err) {
//
//                            callback && callback(err);
//                        });
//                    },
//
//                    // Do login
//                    login: function (credential, callback) {
//                        var that = this;
//                        // Call login
//                        util.callRequest(api.LOGIN.name, api.LOGIN.type, {
//
//                            email: credential.email,
//                            password: credential.password,
//                            keepMelogin: credential.keepMeLogin
//
//                        }).then(function (result) {
//                            // Check result returned
//                            if ('authInfor' in result) {
//                                // Get user info
//                                angular.extend(user.info, result.authInfor);
//                                // Set token
//                                service.token(result.authInfor.userTokens);
//                                // Invoke success authen
//                                that.authenticationSuccessHandler();
//                            } else
//                                callback && callback(result);
//
//                        }, function (err) {
//
//                            callback && callback(err);
//                        });
//                    },
//
//                    // Do logout
//                    logout: function () {
//
//                        // Do logout
//                        util.callRequest(api.LOGOUT.name, api.LOGOUT.type, {
//
//                            authToken: $cookieStore.get(TOKEN_COOKIE_NAME)
//
//                        }).then(function (data) {
//
//                            // Handle error
//
//                        });
//
//                        // Reset authorized info then token
//                        service.reset();
//
//                        // Transition to login
//                        service.authenticationRequiredHandler();
//                    }
//                };
//
//                return service;
//
//            }]);
        // Session
        .factory('Session', [

            '$cookies',
            'api',
            'util',
            '$state',
            '$q',
            'AppConstant',
            'toastr',

            function ($cookies, api, util, $state, $q, AppConstant, toastr) {

                // store current user
                var sessionUser;

                var service = {

                    // Get token from cookie
                    getAccessToken: function () {
                        return $cookies.get(AppConstant.SESSION_COOKIES);
                    },

                    // Set token
                    setAccessToken: function (token) {

                        if (token) {
                            $cookies.put(AppConstant.SESSION_COOKIES, token);
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
                    
                    isLogin : function(){
                       return angular.isObject(sessionUser);
                    },
                    consoleLogin: function (data, callback) {
                        var request = util.callRequest("users/login", "POST", data).then(function (response) {
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

                        var request = util.callRequest("users/logout", "POST", function (response) {

                            callback && callback(response);

                        }).finally(function () {
                            // clear user into session
                            $cookies.remove(AppConstant.SESSION_COOKIES);
                            sessionUser = undefined;
                            // redirect to login page
//                            $state.go('login');
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
                        // validate session
                        if (service.getAccessToken()) {
                            // get user info
                            util.callRequest("/auth/session/data", "GET", {}).then(function (response) {
                                var status = response.status;
                                if (status === 200) {

                                    // store user into session
                                    var userProfile = response.data;
                                    service.setUser(userProfile);
                                    defer.resolve(userProfile);
                                } else {

                                    // clear user into session
                                    $cookies.remove(AppConstant.SESSION_COOKIES);
                                    // show error
                                    var err = _.find(AppConstant, {status: status});
                                    if (err) {
                                        toastr.error($i18next(err.mgsKey));
                                    }
                                    defer.reject();
                                }

                            }, function (errResponse) {

                                // clear user into session
                                $cookies.remove(AppConstant.SESSION_COOKIES);
                                defer.reject(errResponse);

                            }, true);

                        } else {

                            // redirect to login page
//                            $state.go('login');
                            defer.reject();
                        }

                        return defer.promise;
                    }
                };

                return service;
            }
        ]);


