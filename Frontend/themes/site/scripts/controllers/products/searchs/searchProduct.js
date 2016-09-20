'use strict';

angular.module('marketplace.products.search', [])
        .directive('Search', function () {
            var directive = {};

            directive.restrict = 'E'; /* restrict this directive to elements */
//            directive.templateUrl = "/myapp/html-templates/div-template.html";

            return directive;
        })
        .controller("SearchCtrl", ['$scope', 'util', 'ShoppingCart', '$timeout',function ($scope, util, cart, $timeout) {
            $scope.search = function (event) {
                    if (event.which === 13) {
                        util.callRequest('products/filter?searchKey=' + $scope.searchKey, "GET").then(function (data) {
                            $scope.products = data.result;
                        });
                    }
                };
                
                $scope.search();
        }]);
