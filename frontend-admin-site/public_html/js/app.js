'use strict';

define(['theme', 'nprocess'], function (theme, NProgress) {

    var carShow = angular.module('ec-admin', [
        'ngRoute',
        'ngCookies',
        'ngSanitize',
        'ngAnimate',
        'ui.router',
        'ui.select',
        'jm.i18next',
        'oc.lazyLoad',
        'toastr',
        'ui.bootstrap',
        'ec-admin.utils',
        'ec-admin.constant',
        'ec-admin.session',
        'ngTagsInput'
    ])

            // Config ocLazy loading
            .config(['$ocLazyLoadProvider', 'AppConfig', function ($ocLazyLoadProvider, AppConfig) {

                    var modules = [
                        {
                            name: 'loginModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/login/login.js'
                            ]
                        },
                        {
                            name: 'categoriesListModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/categories/list/category_list.js'
                            ]
                        },
                        {
                            name: 'categoriesCreateModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/categories/create/category_create.js'
                            ]
                        },
                        {
                            name: 'categoriesDetailModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/categories/detail/category_detail.js'
                            ]
                        },
                        {
                            name: 'orderListModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/orders/list/orders_list_ctrl.js'
                            ]
                        },

                        {
                            name: 'orderDetailModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/orders/detail/order_detail_ctrl.js'
                            ]
                        },

                        // This is just sample upload page, I will remove later (Quy)
                        {
                            name: 'uploadSampleModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/upload-sample/upload_sample_ctrl.js'
                            ]
                        },
                        {
                            name: 'productsListModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/products/list/product_list.js'
                            ]
                        },
                        {
                            name: 'productsCreateModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/products/create/product_create.js'
                            ]
                        },
                        {
                            name: 'productsDetailModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/products/detail/product_detail.js'
                            ]
                        },
                        {
                            name: 'ListUsersModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/users/list/users_list_ctrl.js'
                            ]
                        },
                        {
                            name: 'UserCreateModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/users/create/users_create_ctrl.js'
                            ]
                        },
                        {
                            name: 'UserEditModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/components/users/edit/users_edit_ctrl.js'
                            ]
                        },
                        {
                            name: 'UserChangePasswordModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/icheck.js',
                                'js/common/directives/directives.js',
                                'js/components/users/change_password/change_password_ctrl.js'
                            ]
                        }
                    ];

                    // Read config value
                    if (angular.isDefined(AppConfig.OCLAZY_CACHE_MODULE)) {
                        angular.forEach(modules, function (v) {
                            v.cache = AppConfig.OCLAZY_CACHE_MODULE;
                        });
                    }

                    // We define some files for a specific module
                    $ocLazyLoadProvider.config({
                        modules: modules
                    });
                }])

            /**
             * UI-Router config with ocLazy load module
             * 
             * @param {type} $stateProvider
             * @param {type} $urlRouterProvider
             * @returns {undefined}
             */
            .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

                    $urlRouterProvider.otherwise('login');

                    $stateProvider
                            .state({
                                name: 'login',
                                url: '/login',
                                templateUrl: 'js/components/login/login.html',
                                controller: 'LoginCtrl as Ctrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('loginModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'authorized',
                                abstract: true,
                                templateUrl: 'js/components/template/authorized.html',
                                controller: ['$scope', 'Session', function ($scope, Session) {

                                        Session.init().then(function () {
                                            // binding session user
                                            $scope.user = Session.getUser();
                                            
                                            $scope.username = $scope.user.firstName + ' ' + $scope.user.lastName;

                                            $scope.logout = function () {
                                                Session.logout();
                                            };
                                        }, function () {
                                            // error handle, show message if necessary
                                        });
                                    }]
                            })
                            .state({
                                name: 'categories',
                                parent: 'authorized',
                                abstract: true,
                                url: '/categories',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'categories.list',
                                url: '/list',
                                templateUrl: 'js/components/categories/list/category_list.html',
                                controller: 'CategoryListCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('categoriesListModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'categories.create',
                                url: '/create',
                                templateUrl: 'js/components/categories/create/category_create.html',
                                controller: 'CategoryCreateCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('categoriesCreateModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'categories.detail',
                                url: '/detail/{id}',
                                templateUrl: 'js/components/categories/detail/category_detail.html',
                                controller: 'CategoryDetailCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('categoriesDetailModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'orders',
                                parent: 'authorized',
                                abstract: true,
                                url: '/orders',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'orders.list',
                                url: '/list',
                                templateUrl: 'js/components/orders/list/orders_list_tmpl.html',
                                controller: 'ListOrderCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('orderListModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'orders.detail',
                                url: '/detail/{id}',
                                templateUrl: 'js/components/orders/detail/order_detail_tmpl.html',
                                controller: 'OrderDetailCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('orderDetailModule');
                                        }]
                                }
                            })

                            // This is just for sample upload I will remove later (Quy)
                            .state({
                                name: 'upload',
                                parent: 'authorized',
                                abstract: true,
                                url: '/upload',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'upload.sample',
                                url: '/sample',
                                templateUrl: 'js/components/upload-sample/upload_sample_tmpl.html',
                                controller: 'UploadSampleCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('uploadSampleModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'products',
                                parent: 'authorized',
                                abstract: true,
                                url: '/products',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'products.list',
                                url: '/list',
                                templateUrl: 'js/components/products/list/product_list.html',
                                controller: 'ProductsListCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('productsListModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'products.create',
                                url: '/create',
                                templateUrl: 'js/components/products/create/product_create.html',
                                controller: 'ProductsCreateCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('productsCreateModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'products.detail',
                                url: '/detail/{id}',
                                templateUrl: 'js/components/products/detail/product_detail.html',
                                controller: 'ProductsDetailCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('productsDetailModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'users',
                                parent: 'authorized',
                                abstract: true,
                                url: '/users',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'users.list',
                                url: '/list',
                                templateUrl: 'js/components/users/list/users_list_tmpl.html',
                                controller: 'ListUsersCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('ListUsersModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'users.create',
                                url: '/create',
                                templateUrl: 'js/components/users/create/users_create.html',
                                controller: 'UserCreateCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('UserCreateModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'users.edit',
                                url: '/{id}',
                                templateUrl: 'js/components/users/edit/users_edit.html',
                                controller: 'UserEditCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('UserEditModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'users.changePassword',
                                url: '/changePassword/{id}',
                                templateUrl: 'js/components/users/change_password/change_password.html',
                                controller: 'ChangePasswordCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('UserChangePasswordModule');
                                        }]
                                }
                            })
                            ;
                }])

            // Config request
            // Set up interceptor
            .config(['$httpProvider', function ($httpProvider) {

                    $httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';

                    /**
                     * The workhorse; converts an object to x-www-form-urlencoded serialization.
                     * @param {Object} obj
                     * @return {String}
                     */
//        var param = function (obj) {
//            var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
//
//            for (name in obj) {
//                value = obj[name];
//
//                if (value instanceof Array) {
//                    for (i = 0; i < value.length; ++i) {
//                        subValue = value[i];
//                        fullSubName = name + '[' + i + ']';
//                        innerObj = {};
//                        innerObj[fullSubName] = subValue;
//                        query += param(innerObj) + '&';
//                    }
//                } else if (value instanceof Object) {
//                    for (subName in value) {
//                        subValue = value[subName];
//                        fullSubName = name + '[' + subName + ']';
//                        innerObj = {};
//                        innerObj[fullSubName] = subValue;
//                        query += param(innerObj) + '&';
//                    }
//                } else if (value !== undefined && value !== null)
//                    query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
//            }
//
//            return query.length ? query.substr(0, query.length - 1) : query;
//        };
//
                    // Override $http service's default transformRequest
//        $httpProvider.defaults.transformRequest = [function (data) {
//            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
//        }];

                    $httpProvider.interceptors.push(function ($q, $cookies) {
                        return {
                            'request': function (config) {
                                config.headers['X-Access-Token'] = $cookies.get('AccessToken');
                                return config;
                            }
                        };
                    });
                }])

            // Angular toastr config
            // see more https://github.com/Foxandxss/angular-toastr
            .config(['toastrConfig', function (toastrConfig) {
                    angular.extend(toastrConfig, {
                        autoDismiss: false,
                        containerId: 'toast-container',
                        maxOpened: 1,
                        newestOnTop: true,
                        positionClass: 'toast-top-center',
                        preventDuplicates: false,
                        preventOpenDuplicates: true,
                        target: 'body'
                    });
                }])

            // ui select config
            .config(['uiSelectConfig', function (uiSelectConfig) {
                    uiSelectConfig.theme = 'bootstrap';
                    uiSelectConfig.resetSearchInput = true;
                    uiSelectConfig.appendToBody = true;
                }])

            .run(['$rootScope', function ($rootScope) {

                    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams, options) {
                        NProgress.start();
                    });

                    // Catch event load page
                    $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {

                    });

                    // Route change error
                    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {

                    });

                    $rootScope.$on('$viewContentLoaded', function (event) {

                        theme.setUpTheme();
                        theme.updateContentHeight();
                        NProgress.done();
                    });

                }]);

    // Set fallback lang will be loaded
    // Config i18next localize 
    angular.module('jm.i18next').config(['$i18nextProvider', function ($i18nextProvider) {
            // Language code: http://4umi.com/web/html/languagecodes.php || http://msdn.microsoft.com/en-us/library/ie/ms533052.aspx
            var lang = window.navigator.browserLanguage || window.navigator.language;
            var language = lang.substr(0, 2);
            if (typeof language === 'undefined' || language !== "ja")
                language = 'en';// set as default 

            console.info("detect browser lang:" + language);
            $i18nextProvider.options = {
                lng: language,
                // selectorAttr: 'data-hg',
                fallbackLng: ["en"], // fallback
                resGetPath: 'i18n/app.' + language + '.json',
                debug: true,
                getAsync: true,
                // preload: ["en", "ja"], // ['en', 'ja'],
                defaultLoadingValue: 'Loading'
            };
        }]);

    return carShow;
});