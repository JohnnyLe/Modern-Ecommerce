'use strict';

angular.module('marketplace.cart', [])

        .controller('CartCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', '$state', 'Session', function ($scope, util, $, $timeout, $stateParams, cart, $state, Session) {
                
                $scope.totalBill = 0;
                $scope.items = cart.getItems();
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



