'use strict';

angular.module('marketplace.home', [])

        .controller('HomeCtrl', ['$scope', 'util', 'ShoppingCart', '$timeout', function ($scope, util, cart, $timeout) {
                // Data model biding
                $scope.loadData = function () {
                    util.callRequest('products', "GET").then(function (data) {
                        $scope.products = data.result;
                    });
                };

                $scope.addToCart = function (product) {
                    cart.addItem(product);
                    console.debug($scope.products);
                };

                
                $scope.loadData();
            }]);
        
 

