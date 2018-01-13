'use strict';

angular.module('marketplace.products.filter', [])

        .controller('ProductFilterCtrl', ['$scope', 'util', '$stateParams', 'ShoppingCart', function ($scope, util, $stateParams, cart) {

                // Data model binding
//                $scope.loadData = function () {
//                    util.callRequest('products/filter', "GET", {'categoryId': $stateParams.categoryId}).then(function (data) {
//                        $scope.products = data.result;
//                    });
//                };
                var param = {
                    categoryId: -1,
                    attributeId: -1,
                    companyId: 1,
                    searchKey: "",
                    minRank: -1,
                    maxRank: -1,
                    minPrice: 0,
                    maxPrice: -1,
                    sortCase: -1,
                    ascSort: 0,
                    pageNumber: 0,
                    pageSize: 12

                };
                $scope.loadListProduct = function (pNumber, pSize) {
//                    var number = Number($stateParams.categoryId);
                    param.categoryId = parseInt($stateParams.categoryId);
                    util.callRequest('/products/filter', "POST", param).then(function (response) {
                        $scope.products = response.data.data;
                        $scope.totalPage = response.data.totalPage;
                    });
                };
                $scope.addToCart = function (product) {
                    cart.addItem(product);
                };

//                $scope.loadData();
                $scope.loadListProduct();
            }]);