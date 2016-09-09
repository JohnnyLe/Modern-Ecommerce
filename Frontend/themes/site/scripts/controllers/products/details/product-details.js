'use strict';

angular.module('marketplace.products.details', [])

.controller('ProductDetailsCtrl', [ '$scope', 'util', '$', '$timeout', function( $scope, util, $, $timeout) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('http://localhost:8383/eCommereTheme/json/product-details.json', "GET", null, true).then(function (data) {
            $scope.product = data.result;
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



