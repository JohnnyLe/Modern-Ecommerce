'use strict';

angular.module('marketplace.products.filter', [])

.controller('ProductFilterCtrl', ['$scope', 'util', '$stateParams', function ($scope, util, $stateParams) {

    // Data model binding
    $scope.loadData = function () {
        util.callRequest('products/filter', "GET", {'categoryId':$stateParams.categoryId}).then(function (data) {
            $scope.products = data.result;
        });
    };

    $scope.loadData();
}]);