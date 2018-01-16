'use strict';

angular.module('marketplace.home', ['bw.paging'])

        .controller('HomeCtrl', ['$scope', 'util', 'ShoppingCart', '$stateParams', 'toastr', 'AppConstant', function ($scope, util, cart, $stateParams, toastr, AppConstant) {

                $scope.pageSize = 12;
                $scope.currentPage = 1;
                $scope.searchKeyShow = "";
                $scope.showResultFound = false;
                $scope.pathFile = AppConstant.pathFile;
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
                    pageSize: 12

                };
                var paramCategory = {
                    search_key: "",
                    sort_case: 1,
                    asc_sort: 0,
                    page_number: 1,
                    page_size: 10
                };
                $scope.loadListProduct = function (pNumber) {
                    paramProduct.pageNumber = pNumber - 1;
                    paramProduct.pageSize = $scope.pageSize;
                    paramProduct.searchKey = $stateParams.search;
                    util.callRequest('products/filter', "POST", paramProduct).then(function (response) {
                        $scope.products = response.data.data;
                        $scope.totalPage = response.data.totalResult;
                        if ($stateParams.search !== "") {
                            $scope.searchKeyShow = $stateParams.search;
                            $scope.showResultFound = true;
                        }
                    });
                };

                $scope.loadPage = function (page) {
                    $scope.loadListProduct(page);
                };

                $scope.addToCart = function (product) {
                    cart.addItem(product);
                };

                $scope.loadListProduct($scope.currentPage);

                // angular event listener for event of search box directive
                $scope.$on('searchResult', function (event, data) {
                    // binding search data into product list
                    $scope.products = data.data;
                    $scope.totalPage = data.totalPage;
                });

                $scope.$on('priceRangeResult', function (event, data) {
                    // user finished sliding a handle
                    paramProduct.minPrice = data.min;
                    paramProduct.maxPrice = data.max;
                    $scope.loadListProduct();
                });
                
                $scope.addToCart = function (product) {
                    var item = {
                        product_id: product.productId,
                        default_image: product.defaultImage,
                        product_name: product.name,
                        price: product.salePrice,
                        quantity: $scope.quantity
                    };
                    cart.addItem(item, item.quantity);
                    util.showSuccessToast("Add to cart successfully");
                };
            }]);

