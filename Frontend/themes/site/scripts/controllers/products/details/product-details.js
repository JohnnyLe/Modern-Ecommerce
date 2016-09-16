'use strict';

angular.module('marketplace.products.details', [])

.controller('ProductDetailsCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', '$http', function ($scope, util, $, $timeout, $stateParams, cart, $http) {

    $scope.proComment = {};
         
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('products/' + $stateParams.productId, "GET").then(function (data) {
            $scope.product = data.result;
        });
        
        util.callRequest('reviews/' + $stateParams.productId, "GET").then(function (data) {
            $scope.reviews = data.result;
        });
    };
    
    $scope.insertComment = function () {
        $scope.proComment.productId = $stateParams.productId;
        $scope.proComment.rank = 1;
        
        util.callRequest('reviews/add?token=' + "dfasdfasdf", "POST", $scope.proComment).then(function (data) {
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


