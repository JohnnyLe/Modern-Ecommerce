'use strict';

angular.module('marketplace.home', [])

.controller('HomeCtrl', [ '$scope', 'util', '$', '$compile', function( $scope, util, $, $compile) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('api/1/products', "GET").then(function (data) {
            $scope.products = data.result;
        });
    };

    $scope.loadData();
}]);

