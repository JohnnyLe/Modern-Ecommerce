'use strict';

angular.module('marketplace.products.details', [])

.controller('ProductDetailsCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', '$http', function ($scope, util, $, $timeout, $stateParams, cart, $http) {

    // Data model biding
    $scope.loadData = function () {
        util.callRequest('products/' + $stateParams.productId, "GET").then(function (data) {
            $scope.product = data.result.product;
            if (data.result.attributes) {
                $scope.productImgs = JSON.parse(data.result.attributes.value_string);
            }
        });
    };
    
    $scope.addToCart = function ( product ) {
        cart.addItem( product );
        
        console.debug( cart.getItems() );
    };

    $scope.loadData();

    $timeout(function () {
        // init slider
        $('#similar-product').carousel({
            interval: 5000
        });
    });
}]);



