'use strict';

angular.module('marketplace.home', [])

.controller('HomeCtrl', [ '$scope', 'util', '$', '$compile', function( $scope, util, $, $compile) {
        
    // Data model biding
    $scope.loadData = function () {
        util.callRequest('jvoid-products', "GET").then(function (data) {
            $scope.product_features = data.products;
 
//            $scope.product_recommand = data.results.recommends;
//  
//            $scope.tshirts = data.results.tshirts;
//      
//            $scope.blazers = data.results.blazers;
//     
//            $scope.product_sunglass = data.results.sunglass;
//        
//            $scope.product_poloshirts = data.results.poloshirts;
//       
//            $scope.product_kids = data.results.kids;
        });
    };

    $scope.loadData();
}]);

