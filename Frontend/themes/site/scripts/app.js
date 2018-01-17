'use strict';

// Declare app level module which depends on views, and controllers
angular.module('marketplace', [
    'ngSanitize',
    'ngCookies',
    'ui.router',
    'ui.bootstrap',
    'jm.i18next',
    'toastr',
    'marketplace.directive',
    'marketplace.authen',
    'marketplace.login',
    'marketplace.register',
    'marketplace.home',
    'marketplace.products.details',
    'marketplace.products.filter',
    'marketplace.cart',
    'marketplace.checkoutdetails'
]).
// Define all route of our app
        config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
                $urlRouterProvider.otherwise("/index");
                // For authentication, but for now just Mock demo.
                // Will be implement in near function
                $stateProvider
                        .state('login', {
                            name: 'login',
                            url: '/login',
                            templateUrl: 'scripts/controllers/login/login.html',
                            controller: 'LoginCtrl'
                        })
                        .state('master', {
                            templateUrl: 'pages/master_tmpl.html',
                            abstract: true,
                            controller: ['$scope', 'Session', '$state', function ($scope, Session, $state) {
                                    Session.init().then(function () {
                                        // binding session user
                                        $scope.user = Session.getUser();
                                        $scope.isLogin = Session.isLogin();
                                        $scope.username = $scope.user.firstName + ' ' + $scope.user.lastName;

                                        $scope.logout = function () {
                                            Session.logout();
                                            $state.reload();
                                        };
                                    }, function () {
                                        // error handle, show message if necessary
                                    });
                                }]
                        })
                        .state('index', {
                            url: '/index?search',
                            params: {
                                search: {
                                    value: '',
                                    squash: true
                                }
                            },
                            parent: 'master',
                            templateUrl: 'scripts/controllers/home/home_tmpl.html',
                            controller: 'HomeCtrl'
                        })
                        .state('shop', {
                            url: '/shop',
                            parent: 'master',
                            templateUrl: 'pages/shop.html'
                        })
                        .state('category-products', {
                            url: '/categories/{categoryId}/products',
                            parent: 'master',
                            templateUrl: 'scripts/controllers/products/filter/product-filter.html',
                            controller: 'ProductFilterCtrl'
                        })
                        .state('product-details', {
                            url: '/products/{productId}',
                            parent: 'master',
                            templateUrl: 'scripts/controllers/products/details/product-details.html',
                            controller: 'ProductDetailsCtrl'
                        })
//                        .state('checkout', {
//                            url: '/checkout',
//                            parent: 'master',
//                            templateUrl: 'pages/checkout.html'
//                        })
                        .state('cart', {
                            url: '/cart',
                            parent: 'master',
                            templateUrl: 'scripts/controllers/cart/cart.html',
                            controller: 'CartCtrl'
                        })
                        .state('checkoutdetails', {
                            url: '/checkout',
                            parent: 'master',
                            templateUrl: 'scripts/controllers/checkout/checkout.html',
                            controller: 'CheckOutCtrl'
                        })

                        .state('register', {
                            url: '/register',
                            parent: 'master',
                            templateUrl: 'scripts/controllers/register/register.html',
                            controller: 'RegisterCtrl'
                        })
                        .state('blog', {
                            url: '/blog',
                            parent: 'master',
                            templateUrl: 'pages/blog.html'
                        })
                        .state('blog-single', {
                            url: '/blog-single',
                            parent: 'master',
                            templateUrl: 'pages/blog-single.html'
                        })
                        .state('contact', {
                            url: '/contact',
                            parent: 'master',
                            templateUrl: 'pages/contact-us.html'
                        })
                        .state('404', {
                            url: '/error-404',
                            templateUrl: 'pages/404.html'
                        });
            }])

// We can define intercepor for $http service
        .config(['$httpProvider', function ($httpProvider) {

                $httpProvider.interceptors.push(function ($q, $injector, $location, $timeout, $cookies) {

                    var api = $injector.get('api'),
                            app = $injector.get('app'),
                            error = $injector.get('error'),
                            cookie = $injector.get('$cookieStore');

                    // Init fix token


                    // Lodash
                    var _ = $injector.get('_');

                    // Noty toast
                    var noty = $injector.get('noty');

                    // Manage just one instance of noty
                    var notyInstance;

                    function tokenExpiredHandler() {

                        // Transition to login page
                        $timeout(function () {

                            // Clear token
                            cookie.remove(app.COOKIE_NAME);
                            // Redirect
                            // $location.path( '/login' );
                        });
                    }

                    return {

                        request: function (config) {
                            config.headers['X-Access-Token'] = $cookies.get('AccessToken');
                            // Loop to find 
                            angular.forEach(api, function (a) {

                                if (a.token && config.url.indexOf(a.name) > 0) {

                                    // Add token to request
                                    config.data.token = cookie.get(app.COOKIE_NAME);
                                }
                            });

                            return config;
                        },

                        response: function (response) {

                            var contentType = response.headers()['content-type'];

                            contentType = (contentType) ? contentType.toLowerCase() : null;

                            if (contentType === "application/json;charset=utf-8") {

                                var status = response.data;

                                // Get error code
                                if ('errCode' in status) {

                                    var errCode = status.errCode;

                                    if (errCode > 0) {

                                        // Check authen
                                        if (_.find(error.AUTH, {code: errCode}) !== undefined) {

                                            // Handler
                                            tokenExpiredHandler();

                                            return;
                                        }

                                        // Handle other errors
                                        // Show error as toast
                                        var e = _.find(error.OTHERS, {code: errCode});

                                        if (e !== undefined) {

                                            var notyOpts = {

                                                text: "There's an erorr occur",
                                                type: 'error', // success, information ...
                                                theme: 'bootstrapTheme',
                                                layout: 'top',
                                                closeWith: ['button', 'click']
                                                        // timeout: 5000 // 5s
                                            };

                                            var opts = {

                                                text: e.desc
                                            };

                                            // Check instance
                                            if (notyInstance) {

                                                // Close & clean up
                                                notyInstance.closeCleanUp();
                                            }

                                            notyInstance = noty(angular.extend(notyOpts, opts));
                                        }
                                    }
                                }
                            }

                            return response || $q.when(response);
                        },

                        responseError: function (rejection) {

                            // Handle reponse error
                            return $q.reject(rejection);
                        }
                    };
                });

            }])

// Init app
        .run(['$rootScope', '$', function ($rootScope, $) {

                //user.init();

                // Catch event load page
                $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams, options) {
                    $rootScope.menu = toState.name;
                    // scroll to top
                    $('html, body').animate({
                        scrollTop: 0
                    }, 0);
                });

                $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams, options) {

                    // Intecepter request
                });

                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, options) {

                    // I think we should init all control that we have
                    // console.log( 'We reject this route:', rejection );

                });

                // scroll to top
                $.scrollUp({
                    scrollName: 'scrollUp', // Element ID
                    scrollDistance: 300, // Distance from top/bottom before showing element (px)
                    scrollFrom: 'top', // 'top' or 'bottom'
                    scrollSpeed: 300, // Speed back to top (ms)
                    easingType: 'linear', // Scroll to top easing (see http://easings.net/)
                    animation: 'fade', // Fade, slide, none
                    animationSpeed: 200, // Animation in speed (ms)
                    scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
                    //scrollTarget: false, // Set a custom target element for scrolling to the top
                    scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
                    scrollTitle: false, // Set a custom <a> title if required.
                    scrollImg: false, // Set true to use image
                    activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
                    zIndex: 2147483647 // Z-Index for the overlay
                });

            }]);

// I18N
angular.module('jm.i18next').config(['$i18nextProvider', function ($i18nextProvider) {

        // Configure multi language
        $i18nextProvider.options = {
            lng: 'en',
            useCookie: false,
            useLocalStorage: false,
            debug: true,
            // fallbackLng: 'dev',
            resGetPath: 'i18n/en.json',
            defaultLoadingValue: 'Loading' // ng-i18next option, *NOT* directly supported by i18next
        };
    }]);