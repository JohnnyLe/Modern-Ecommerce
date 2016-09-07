'use strict';

angular.module('marketplace.home', [])

.controller('HomeCtrl', [ '$scope', 'util', '$', '$compile', function( $scope, util, $, $compile) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('api/products', "GET").then(function (data) {
            $scope.products = data.results;
        });
    };

    $scope.loadData();
}]);

