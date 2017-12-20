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
        'ec-admin.session'
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
                                'js/components/categories/list/categories_list_ctrl.js'
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
                                templateUrl: 'js/components/categories/list/categories_list_tmpl.html',
                                controller: 'ListCategoriesCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('categoriesListModule');
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