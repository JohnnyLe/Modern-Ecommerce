/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';

angular.module('marketplace')

// Utilities for our app
        .factory('util', ['$http', '$q', 'server', '$log', '_', 'api', '$modal', '$i18next', function ($http, $q, server, $log, _, api, $modal, $i18next) {

                // Check array has all empty value or not
                // Example [ '', '', '' ]
                function isAllValueEmpty(array) {

                    var check = true;

                    _.forEach(array, function (item) {

                        if (item && item !== null && item !== '') {

                            check = false;
                        }
                    });

                    return check;
                }

                return {

                    // Create interceptor request
                    callRequest: function (api, method, data, abstractUrl) {

                        var apiName = api, m = method, d = data;

                        if (angular.isObject(api)) {

                            apiName = api.name;
                            m = api.type || api.method;

                            d = method;
                        }

                        var URL = abstractUrl ? apiName : server.URL + '/' + apiName;

                        var dfd = $q.defer();

                        var callRequest = {
                            url: URL
                        };

                        if (m.toUpperCase() === 'GET') {
                            callRequest.method = 'GET';
                            callRequest.params = d;
                        } else {
                            callRequest.method = m || 'POST';
                            callRequest.data = d;
                        }

                        $http(callRequest).then(function (result) {

                            // Redirect could be dismiss data
                            // It happens when a request is posting while transition is executing
                            if (typeof result === 'undefined') {

                                dfd.reject(data);

                                return;
                            }

                            var data = result.data;

                            // Contain any error
                            if ('errCode' in data && data.errCode > 0 && data.status !== 'OK') {

                                // Support trace error
                                $log.log("Request error: ", api, data);

                                dfd.reject(data);
                            } else
                                dfd.resolve(data);


                        }, function (err) {

                            // We can handle this error at interceptor
                            dfd.reject(err);
                        });

                        return dfd.promise;
                    },

                    // Get text from path
                    translate: function (path) {

                        return $i18next(path);
                    },

                    // Escape HTML
                    escape: function (text) {

                        return _.escape(text);
                    },

                    // Unescape HTML
                    unescape: function (text) {

                        return _.unescape(text);
                    },

                    // When using object to store param, you can be easy to extend your method but a little difficult to use
                    // So that It's all up to you based on the context of using the method
                    // @param {object} opts : The setting of modal
                    // @param {function} dismissCallback : The dismiss callback
                    openModal: function (opts, dismissCallback) {

                        var setting = {

                            backdrop: 'static',
                            templateUrl: opts.tmpl,
                            controller: opts.ctrl
                        };

                        if (opts.data) {

                            setting.resolve = {

                                data: function () {

                                    return opts.data;
                                }
                            };
                        }

                        var instance = $modal.open(setting);

                        // Bind event dismiss modal
                        instance.result.then(function (type) {

                            if (typeof dismissCallback === 'function')
                                dismissCallback(type);

                        });

                        return instance;
                    },

                    isInt: function (value) {
                        return !isNaN(value) &&
                                parseInt(Number(value)) === value &&
                                !isNaN(parseInt(value, 10));
                    }


                };
            }])

        .factory('ShoppingCart', ['util', '$cookies', '$q', function (util, $cookies, $q) {

                // update an item is gotten from server and update quantity for it            
                function getStoredQuantity(items, itemId) {
                    for (var i = 0; i < items.length; i++) {
                        if (items[i].product_id === itemId) {
                            return items[i].quantity;
                        }
                    }

                    // default quantity is 1
                    return 1;
                }

                return {
                    // add a product into shopping cart
                    addItem: function (product, quantity) {
                        // just add product if 'p' parameter is object
                        if (angular.isObject(product)) {
                            // check product already exists in cart
                            if (product.product_id) {
                                var items = $cookies.getObject('cartItems');

                                if (items && angular.isArray(items)) {
                                    for (var i = 0; i < items.length; i++) {
                                        if (items[i].product_id === product.product_id) {
                                            // if there are quantity parameter then update quantity for product
                                            if (util.isInt(quantity)) {
                                                items[i].quantity = quantity;
                                                // update cookie
                                                $cookies.putObject('cartItems', items);
                                            }
                                            return;
                                        }
                                    }
                                } else {
                                    // empty cart, create new item list
                                    items = [];
                                }

                                // add product with quantity into item list
                                var item = {
                                    product_id: product.product_id,
                                    default_image: product.default_image,
                                    product_name: product.product_name,
                                    quantity: util.isInt(quantity) ? quantity : 1,
                                    price: product.price
                                };
                                items.push(item);
                                // save into cookie
                                $cookies.putObject('cartItems', items);
                            }
                        }
                    },

                    // remove item
                    removeItem: function (itemId) {
                        var items = $cookies.getObject('cartItems'), index = -1;

                        if (items && angular.isArray(items)) {
                            for (var i = 0; i < items.length; i++) {
                                if (items[i].product_id === itemId) {
                                    index = i;
                                    break;
                                }
                            }
                        }

                        if (index !== -1) {
                            console.debug('remove item', index);
                            // remove the item outside array
                            items.splice(index, 1);
                            // restore item list
                            $cookies.remove('cartItem');
                            $cookies.putObject('cartItems', items);
                        }
                    },

                    clearCart: function () {
                        var clearItems = [];
                        $cookies.remove('cartItem');
                        $cookies.putObject('cartItems', clearItems);
                    },

                    // load product ids from cookies and get full information of product by list id
                    getItems: function () {
                        var items = $cookies.getObject('cartItems');
                        return items;
//                        var defer = $q.defer();
//
//                        // get items are stored in cookies
//                        var items = $cookies.getObject('cartItems');
//
//                        // check cookies is exists and must be an array
//                        if (items && angular.isArray(items)) {
//                            //general list product id
//                            var ids = [];
//
//                            for (var i = 0; i < items.length; i++) {
//                                ids.push(items[i].product_id);
//                            }
//
//                            if (ids.length > 0) {
//                                // get product list by ids
//                                util.callRequest('products/list', 'POST', ids).then(function (data) {
//                                    var cartItems = [], result = data.result, tmp;
//
//                                    if (result) {
//                                        for (var i = 0; i < result.length; i++) {
//                                            tmp = result[i];
//                                            // update quantity for item
//                                            tmp.quantity = getStoredQuantity(items, tmp.product_id);
//                                            cartItems.push(tmp);
//                                        }
//                                    }
//
//                                    defer.resolve(cartItems);
//                                });
//                            }
//                        } else {
//                            items = [];
//                            defer.resolve(items);
//                        }
//
//                        return defer.promise;
                    }
                };
            }]);
