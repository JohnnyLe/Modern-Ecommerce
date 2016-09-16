'use strict';

angular.module('marketplace.home', [ 'bw.paging' ])

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
        
    // Data model biding
    $scope.loadData = function (pNumber, pSize) {
        util.callRequest('products', "GET", {pageNumber: pNumber, pageSize: pSize}).then(function (data) {
            $scope.products = data.result;
            $scope.totalPage = data.total_records;
        });
    };

    $scope.loadPage = function (page) {
        $scope.loadData(page - 1, $scope.pageSize);
    };

    $scope.addToCart = function (product) {
        cart.addItem(product);
    };

    $scope.loadData(0, $scope.pageSize);
    
}]);

