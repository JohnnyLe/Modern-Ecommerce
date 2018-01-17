'use strict';

angular.module('marketplace.products.filter', [])

        .controller('ProductFilterCtrl', ['$scope', 'util', '$stateParams', 'ShoppingCart', 'AppConstant', function ($scope, util, $stateParams, cart, AppConstant) {

                // Data model binding
//                $scope.loadData = function () {
//                    util.callRequest('products/filter', "GET", {'categoryId': $stateParams.categoryId}).then(function (data) {
//                        $scope.products = data.result;
//                    });
//                };
                $scope.pageSize = 12;
                $scope.currentPage = 1;
                $scope.pathFile = AppConstant.pathFile;
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
                    pageSize: 3

                };
                $scope.loadListProduct = function (pNumber) {
                    param.categoryId = parseInt($stateParams.categoryId);
                    param.pageNumber = pNumber - 1;
                    param.pageSize = $scope.pageSize;
                    util.callRequest('/products/filter', "POST", param).then(function (response) {
                        $scope.products = response.data.data;
                        $scope.totalPage = response.data.totalResult;
                    });
                };
                $scope.addToCart = function (product) {
                    cart.addItem(product);
                };
                
                $scope.loadPage = function (page) {
                    $scope.loadListProduct(page);
                };
                
                $scope.$on('priceRangeResult', function (event, data) {
                    console.log(data);
                    // user finished sliding a handle
                    param.minPrice = data.min;
                    param.maxPrice = data.max;
                    $scope.loadListProduct();
                });

//                $scope.loadData();
                $scope.loadListProduct($scope.currentPage);
            }]);