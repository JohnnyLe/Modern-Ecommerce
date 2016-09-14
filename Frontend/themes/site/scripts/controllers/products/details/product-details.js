'use strict';

angular.module('marketplace.products.details', [])

.controller('ProductDetailsCtrl', [ '$scope', 'util', '$', '$timeout', '$stateParams', function( $scope, util, $, $timeout, $stateParams) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('products/' + $stateParams.productId, "GET").then(function (data) {
            $scope.product = data.result;
        });
        
        util.callRequest('reviews/' + $stateParams.productId, "GET").then(function (data) {
            $scope.reviews = data.result;
        });
    };

    $scope.loadData();
    
    $timeout(function () {
        // init silder
        $('#similar-product').carousel({
            interval: 5000
        });
    });
}]);



