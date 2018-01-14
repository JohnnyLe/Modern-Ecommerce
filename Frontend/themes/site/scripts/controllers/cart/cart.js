'use strict';

angular.module('marketplace.cart', [])

        .controller('CartCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', '$state', function ($scope, util, $, $timeout, $stateParams, cart, $state) {

                $scope.totalBill = 0;
                $scope.addToCart = function () {

                    var item = {
                        product_id: "101",
                        default_image: "../images/p8.jpg",
                        product_name: "Product 101",
                        price: 130,
                        quantity: 3
                    };
                    cart.addItem(item, item.quantity);
                };

                $scope.calculateTotal = function () {
                    var itemList = cart.getItems();
                    if (typeof itemList !== 'undefined') {
                        for (var i = 0; i < itemList.length; i++) {
                            $scope.totalBill += itemList[i].price * itemList[i].quantity;
                        }
                    }

                };

                $scope.calculateTotal();

                $scope.goCheckOut = function () {
                    $state.go('checkoutdetails');
                };


            }]);



