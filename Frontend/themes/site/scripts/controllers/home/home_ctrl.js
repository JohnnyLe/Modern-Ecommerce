'use strict';

angular.module('marketplace.home', [])

.controller('HomeCtrl', [ '$scope', 'util', 'ShoppingCart', function( $scope, util, cart) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('products', "GET").then(function (data) {
            $scope.products = data.result;
        });
    };
    
    $scope.addToCart = function ( product ) {
        cart.addItem( product );
    };

    $scope.loadData();
}]);

