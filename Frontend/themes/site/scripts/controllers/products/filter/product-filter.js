'use strict';

angular.module('marketplace.products.filter', [])

.controller('ProductFilterCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', function ($scope, util, $, $timeout, $stateParams) {

    // Data model biding
    $scope.loadData = function () {
        var pData = {
            'categoryId': $stateParams.categoryId
        };
        
        util.callRequest('products/filter', "GET", pData).then(function (data) {
            $scope.products = data.result;
        });
    };

    $scope.loadData();

    $timeout(function () {
        // init slider
        $('#similar-product').carousel({
            interval: 5000
        });
    });
}]);