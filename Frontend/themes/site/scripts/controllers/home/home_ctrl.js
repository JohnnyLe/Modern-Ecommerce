'use strict';

angular.module('marketplace.home', ['bw.paging'])

        .controller('HomeCtrl', ['$scope', 'util', 'ShoppingCart', '$stateParams', function ($scope, util, cart, $stateParams) {

                $scope.pageSize = 10;
                var paramProduct = {
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
                    pageSize: 10

                };
                var paramCategory = {
                    search_key: "",
                    sort_case: 1,
                    asc_sort: 0,
                    page_number: 1,
                    page_size: 10
                };
                $scope.loadListProduct = function (pNumber, pSize) {
                    paramProduct.searchKey = $stateParams.search;
                    util.callRequest('/products/filter', "POST", paramProduct).then(function (response) {
                        $scope.products = response.data.data;
                        $scope.totalPage = response.data.totalPage;
                    });
                };

                $scope.loadPage = function (page) {
                    $scope.loadData(page - 1, $scope.pageSize);
                };

                $scope.addToCart = function (product) {
                    cart.addItem(product);
                };

                $scope.loadListProduct(0, $scope.pageSize);

                // angular event listener for event of search box directive
                $scope.$on('searchResult', function (event, data) {
                    // binding search data into product list
                    $scope.products = data.data;
                    $scope.totalPage = data.totalPage;
                });

                $scope.$on("priceRangeResult", function (event, data) {
                    // user finished sliding a handle
                    console.debug(data);
                });

            }]);

