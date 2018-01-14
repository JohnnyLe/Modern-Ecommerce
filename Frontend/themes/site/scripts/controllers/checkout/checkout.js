'use strict';
angular.module('marketplace.checkoutdetails', [])

        .controller('CheckOutCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', function ($scope, util, $, $timeout, $stateParams, cart) {

                var isLogin = true;
                $scope.emailPattern = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
                $scope.phoneNumber = /^\+?[0-9]\d{1,16}$/;
                $scope.submitting = false;
                $scope.items = cart.getItems();
                $scope.user = {
                    "userId": "",
                    "firstName":"",
                    "lastName": "",
                    "middleName": "",
                    "email":"",
                    "phone": "",
                    "fax": "",
                    "address": "",
                    "city":"",
                    "country": ""
                };

                $scope.createOrder = function () {
                    $scope.submitting = true; 
                    var params = {};
                    var products = [];
                    for (var i = 0; i < $scope.items.length; i++) {
                        var product = {
                            "productId": "",
                            "quantity": 1
                        };
                        product.productId = $scope.items[i].product_id;
                        product.quantity = $scope.items[i].quantity;
                        products[i] = product;
                    }
                    params.user = isLogin ? angular.copy($scope.user): null;
                    params.productList = products;
                    console.log("param", params);
                    util.callRequest('orders/create', "POST", params).then(function (response) {
                        console.log("response", response.data);
                        
                    });
                    $scope.submitting = false;
                };
            }]);



