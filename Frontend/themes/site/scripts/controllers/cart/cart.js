'use strict';

angular.module( 'marketplace.cart', [] )

.controller('CartCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', function ($scope, util, $, $timeout, $stateParams, cart) {

    cart.getItems().then( function( items ) {
        $scope.items = items;
    });
    
}]);



