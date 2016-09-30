'use strict';

angular.module('marketplace.products.filter', [])

.controller('ProductFilterCtrl', ['$scope', 'util', '$stateParams', 'ShoppingCart', function ($scope, util, $stateParams, cart) {

    // Data model binding
    $scope.loadData = function () {
        util.callRequest('products/filter', "GET", {'categoryId':$stateParams.categoryId}).then(function (data) {
            $scope.products = data.result;
        });
    };

    $scope.addToCart = function (product) {
        cart.addItem(product);
    };

    $scope.loadData();
}]);